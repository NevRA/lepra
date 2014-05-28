package org.koroed.lepra.content.parser;

import org.json.JSONObject;
import org.koroed.lepra.LepraStatus;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 28.05.2014
 * Time: 17:36
 */
public class LepraStatusParser extends LepraContentParser<LepraStatus> {

    private static LepraStatusParser instance = new LepraStatusParser();

    public static LepraStatusParser getInstance() {
        return instance;
    }

    private LepraStatusParser() {
    }

    @Override
    protected LepraStatus parseContent(String content) {
        if(!content.startsWith("{")) {
            return null;
        }
        JSONObject obj = new JSONObject(content);
        int karma = obj.getInt("karma");
        int rating = obj.getInt("rating");
        int voteWeight = obj.getInt("voteweight");
        int myUnreadPosts = obj.getInt("myunreadposts");
        int myUnreadComms = obj.getInt("myunreadcomms");
        int inboxUnreadPosts = obj.getInt("inboxunreadposts");
        int inboxUnreadComms = obj.getInt("inboxunreadcomms");
        return new LepraStatus(karma, rating, voteWeight, myUnreadPosts, myUnreadComms, inboxUnreadPosts, inboxUnreadComms);
    }
}
