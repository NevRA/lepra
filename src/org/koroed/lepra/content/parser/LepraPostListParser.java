package org.koroed.lepra.content.parser;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.koroed.lepra.content.LepraPost;
import org.koroed.lepra.loader.LepraNewContentHandler;

import java.util.Date;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 19:30
 */
public class LepraPostListParser extends LepraContentParser<Integer> {

    private static String POST_SEPARATOR = "\\n\\n\\t\\t\\n\\t\\t\\t\\t";

    private LepraNewContentHandler<LepraPost> handler;

    public LepraPostListParser(LepraNewContentHandler<LepraPost> handler) {
        this.handler = handler;
    }

    @Override
    protected Integer parseContent(String content) {

        if(!content.contains("{")) {
            System.out.println(content);
            return null;
        }

        content = content.substring(content.indexOf("{"));

        Integer newOffset = null;
        String template = null;
        try {
            JSONObject obj = new JSONObject(content);
            template = obj.getString("template");
            newOffset = obj.getInt("offset");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(template)) {
            return null;
        }

//        if(template.indexOf(POST_SEPARATOR) < 0) {
//            return Collections.emptyList();
//        }
        String[] rawPostArray =  template.split(POST_SEPARATOR, -1);

        for(String rawPost : rawPostArray) {
            if(StringUtils.isNotBlank(rawPost)){
                handler.processContent(postParser(rawPost));
            }
        }
        return newOffset;
    }

    private LepraPost postParser(String rawPost) {
        Document doc = Jsoup.parse(rawPost);
        String postContent =  doc.select(".dti").first().html();
        String userLogin =  doc.select(".c_user").first().text();
        String commentsCnt = null, newCommentsCnt = null;
        Element commentsCounts = doc.select(".b-post_comments_links").first();
        if(commentsCounts != null) {
           Elements cnts =  commentsCounts.getElementsByTag("a");
           if(!cnts.isEmpty()) {
               commentsCnt = cnts.first().text();
               if(cnts.size() > 1) {
                   newCommentsCnt = cnts.get(1).text();
               }
           }
        }

        String d = doc.select(".js-date").first().attr("data-epoch_date");

        long date =  Long.valueOf(d);

        String title = doc.select(".ddi").first().html();
        if(StringUtils.isNotBlank(title)) {
            title = title.trim().substring(8, title.indexOf("<a")).replaceAll("\\s", " ").trim();
        }
        return new LepraPost(userLogin, title, new Date(date * 1000), commentsCnt, newCommentsCnt, postContent);
    }
}
