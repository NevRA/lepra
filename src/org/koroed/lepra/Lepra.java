package org.koroed.lepra;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.koroed.lepra.content.LepraPost;
import org.koroed.lepra.content.LepraProfile;
import org.koroed.lepra.content.LepraUser;
import org.koroed.lepra.content.parser.*;
import org.koroed.lepra.loader.LepraAsyncContentListLoader;
import org.koroed.lepra.loader.LepraNewContentHandler;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
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
        return lepraUser;
    }

    public LepraStatus getLepraStatus() {
        return httpClient.loadContent(LepraURI.LEPROPANEL, LepraStatusParser.getInstance());
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

    public LepraAsyncContentListLoader getMyThingsListLoader(LepraNewContentHandler<LepraPost> handler) {
        LepraAsyncContentListLoader l = getPostListLoader(LepraURI.MY_THINGS, "1", handler);
        l.setPeriod(30);
        l.setUnread(0);
        return l;
    }

    public LepraAsyncContentListLoader getInboxListLoader(LepraNewContentHandler<LepraPost> handler) {
        LepraAsyncContentListLoader l = getPostListLoader(LepraURI.INBOX, "1", handler);
        l.setPeriod(30);
        l.setUnread(1);
        return l;
    }

    public LepraAsyncContentListLoader getFavListLoader(String leprosorium, LepraNewContentHandler<LepraPost> handler) {
        return getPostListLoader(LepraURI.FAV, "last_activity", handler);
    }

    public String getContextJson() {
        return new Gson().toJson(httpClient.cookieStore.getCookies());
    }

    public void initFromUserContextJson(String json) {
        Type type = new TypeToken<List<BasicClientCookie>>(){}.getType();
        List<Cookie> cokies =  new Gson().fromJson(json, type);
        for(Cookie c : cokies) {
            httpClient.cookieStore.addCookie(c);
        }
        ctx = new LepraContext(new LepraUser());
        httpClient.loadContent(LepraURI.USERS, new CurrentUserInfoParser(ctx));
    }





}
