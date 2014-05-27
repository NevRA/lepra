package org.koroed.lepra.content.parser;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.koroed.lepra.LepraContext;
import org.koroed.lepra.LepraException;
import org.koroed.lepra.content.LepraUser;

import java.util.Date;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 19:59
 */
public class CurrentUserInfoParser extends LepraContentParser<LepraContext> {

    LepraContext lepraContext;

    public CurrentUserInfoParser(LepraContext lepraContext) {
        this.lepraContext = lepraContext;
    }

    @Override
    protected LepraContext parseContent(String content) throws LepraException {
        int indexOfUserData = content.indexOf("globals.user");
        if(indexOfUserData < 0) {
            return lepraContext;
        }

        content = content.substring(indexOfUserData);
        content = content.substring(0, content.indexOf("};"));
        content = content.substring(content.indexOf("{")) + "};";

        JSONObject obj = new JSONObject(content);

        if(!StringUtils.equals(lepraContext.getUser().getLogin(),obj.getString("login"))) {
            throw new LepraException("Unknown error");
        }

        lepraContext.setCsrfToken(obj.getString("csrf_token"));
        lepraContext.getUser().setCreated(new Date(obj.getLong("created") * 1000));
        lepraContext.getUser().setInvitedById(obj.getInt("invited_by_id"));

        return lepraContext;
    }
}
