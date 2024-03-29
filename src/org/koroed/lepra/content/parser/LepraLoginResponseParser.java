package org.koroed.lepra.content.parser;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.koroed.lepra.LepraException;
import org.koroed.lepra.content.LepraUser;
import org.koroed.lepra.login.LepraCaptchaRequired;
import org.koroed.lepra.login.LepraInvalidLoginPasswordException;
import org.koroed.lepra.login.LepraLoginException;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 11:37
 */
public class LepraLoginResponseParser extends LepraContentParser<LepraUser> {
    private static LepraLoginResponseParser instance = new LepraLoginResponseParser();

    public static LepraLoginResponseParser getInstance() {
        return instance;
    }

    private LepraLoginResponseParser() {
    }

    public LepraUser parseContent(String content) throws LepraException {
        if (StringUtils.isBlank(content) || "null".equalsIgnoreCase(content.trim())) {
            throw new LepraLoginException();
        }
        JSONObject obj = new JSONObject(content);
        String status = obj.getString("status");
        if (status == null || !"OK".equalsIgnoreCase(status)) {
            JSONArray errors = obj.optJSONArray("errors");
            if(errors != null && errors.length() > 0) {
                for(int i = 0; i <errors.length(); i ++ ) {
                    JSONObject error = errors.getJSONObject(i);
                    if(error != null && StringUtils.equalsIgnoreCase(error.getString("code"), "invalid_password")) {
                        throw new LepraInvalidLoginPasswordException();
                    }
                    if(error != null && StringUtils.equalsIgnoreCase(error.getString("code"), "captcha_required")) {
                        throw new LepraCaptchaRequired();
                    }
                }
            }
            throw new LepraLoginException();
        }
        JSONObject user = obj.getJSONObject("user");
        return new LepraUser(user.getInt("id"), user.getString("login"), user.getString("gender"), user.getInt("karma"));
    }
}
