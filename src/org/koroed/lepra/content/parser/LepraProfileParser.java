package org.koroed.lepra.content.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.koroed.lepra.content.LepraProfile;
import org.koroed.lepra.content.LepraUser;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 13:02
 */
public class LepraProfileParser extends LepraContentParser<LepraProfile> {

    private static LepraProfileParser instance = new LepraProfileParser();

    public static LepraProfileParser getInstance() {
        return instance;
    }

    private LepraProfileParser() {
    }

    @Override
    public LepraProfile parseContent(String content) {
        Document doc = Jsoup.parse(content);

        String userName = getFirstElementText(doc, ".b-user_name-link");
        String userFullName = getFirstElementText(doc, ".b-user_full_name");
        String userResidence = getFirstElementText(doc, ".bb-user_residence");
        String userText = getFirstElementHtml(doc, ".b-user_text");
        String userPic = null;
        Elements up = doc.select(".b-userpic").first().getElementsByTag("img");
        if(up != null && !up.isEmpty()) {
            userPic = up.first().absUrl("src");
        }
        LepraUser lu = new LepraUser(null, userName , null, null);

        return new LepraProfile(lu, userFullName, userResidence, userText, userPic);
    }

    private String getFirstElementHtml(Document doc, String cssQuery) {
        Element e = doc.select(cssQuery).first();
        if(e != null) {
            return e.html();
        }
        return null;
    }

    private String getFirstElementText(Document doc, String cssQuery) {
        Element e = doc.select(cssQuery).first();
        if(e != null) {
            return e.text();
        }
        return null;
    }
}
