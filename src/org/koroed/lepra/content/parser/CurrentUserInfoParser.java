package org.koroed.lepra.content.parser;

import org.json.JSONObject;
import org.koroed.lepra.LepraContext;
import org.koroed.lepra.LepraException;

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

        lepraContext.getUser().setLogin(obj.getString("login"));
        lepraContext.getUser().setId(obj.getInt("id"));
        lepraContext.getUser().setGender(obj.getString("gender"));
        lepraContext.getUser().setKarma(obj.getInt("karma"));

        lepraContext.setCsrfToken(obj.getString("csrf_token"));
        lepraContext.getUser().setCreated(new Date(obj.getLong("created") * 1000));
        lepraContext.getUser().setInvitedById(obj.getInt("invited_by_id"));

        return lepraContext;
    }
}
