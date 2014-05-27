package org.koroed.lepra;

import org.koroed.lepra.content.LepraUser;

import java.io.Serializable;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 11:30
 */
public class LepraContext implements Serializable {
    private LepraUser user;
    private String cookieSid;
    private String cookieUid;
    private String csrfToken;

    public LepraContext(LepraUser user) {
        this.user = user;
    }

    public LepraUser getUser() {
        return user;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    @Override
    public String toString() {
        return "LepraContext{" +
                "user=" + user +
                ", cookieSid='" + cookieSid + '\'' +
                ", cookieUid='" + cookieUid + '\'' +
                ", csrfToken='" + csrfToken + '\'' +
                '}';
    }
}
