package org.koroed.lepra;

import org.apache.commons.lang3.StringUtils;
import org.koroed.lepra.content.LepraPost;
import org.koroed.lepra.content.LepraProfile;
import org.koroed.lepra.content.LepraUser;
import org.koroed.lepra.content.parser.*;
import org.koroed.lepra.loader.LepraAsyncContentListLoader;
import org.koroed.lepra.loader.LepraNewContentHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 14.05.14
 * Time: 11:28
 */
public class Lepra {

    private static Lepra instance = new Lepra();
    private LepraHttpClient httpClient = new LepraHttpClient();
    private LepraContext ctx;

    private Lepra() {
    }

    public static Lepra getInstance() {
        return instance;
    }

    @Deprecated
    public static Lepra getNewInstance() {
        return new Lepra();
    }

    public LepraUser login(String username, String password, boolean forever) {
        return login(username, password, forever, null, null);
    }

    public LepraUser login(String username, String password, boolean forever, String recaptchaChallengeField, String recaptchaResponseField) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("forever", forever ? "1" : "0");
        if(!StringUtils.isBlank(recaptchaChallengeField)) {
            parameters.put("recaptcha_challenge_field", recaptchaChallengeField);
        }
        if(!StringUtils.isBlank(recaptchaResponseField)) {
            parameters.put("recaptcha_response_field", recaptchaResponseField);
        }
        LepraUser lepraUser = httpClient.loadContent(LepraURI.LOGIN, parameters, LepraLoginResponseParser.getInstance());
        ctx = new LepraContext(lepraUser);

        httpClient.loadContent(LepraURI.getProfileURI(lepraUser.getLogin()), new CurrentUserInfoParser(ctx));
        System.out.println(ctx);
        return lepraUser;
    }

    public void logout() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("csrf_token", ctx.getCsrfToken());
        httpClient.loadContent(LepraURI.LOGOUT, parameters, LepraEmptyContentParser.getInstance());
        ctx = null;
    }

    public LepraProfile loadProfile(String login) {
        return httpClient.loadContent(LepraURI.getProfileURI(login), LepraProfileParser.getInstance());
    }

    private LepraAsyncContentListLoader getPostListLoader(URI uri, String sorting, LepraNewContentHandler<LepraPost> handler) {
        return new LepraAsyncContentListLoader(uri, new LepraPostListParser(handler), sorting, ctx, httpClient);
    }

    public LepraAsyncContentListLoader getPostListLoader(String leprosorium, LepraNewContentHandler<LepraPost> handler) {
        return getPostListLoader(LepraURI.getPostListURI(leprosorium), "last_activity", handler);
    }

}
