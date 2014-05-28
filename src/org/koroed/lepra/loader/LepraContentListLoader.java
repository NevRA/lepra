package org.koroed.lepra.loader;

import org.koroed.lepra.LepraContext;
import org.koroed.lepra.LepraHttpClient;
import org.koroed.lepra.content.parser.LepraContentParser;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 28.05.2014
 * Time: 10:36
 */
public class LepraContentListLoader {
    private LepraContentParser<Integer> parser;
    private Integer offset = 0;
    private Integer period;
    private Integer unread;
    private String sorting;
    private URI uri;
    private LepraContext ctx;
    private LepraHttpClient httpClient;

    public LepraContentListLoader(URI uri, LepraContentParser<Integer> parser, String sorting, LepraContext ctx, LepraHttpClient httpClient) {
        this.parser = parser;
        this.sorting = sorting;
        this.uri = uri;
        this.ctx = ctx;
        this.httpClient = httpClient;
    }

    public void load() {
        if(!hasNext()) {
            return;
        }
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("csrf_token", ctx.getCsrfToken());
        parameters.put("offset", Integer.toString(offset));
        parameters.put("sorting", sorting);
        if(period != null) {
            parameters.put("period", period.toString());
        }
        if(unread != null) {
            parameters.put("unread", unread.toString());
        }
        offset = httpClient.loadContent(uri, parameters, parser);
    }

    public boolean hasNext() {
        return offset != null;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }
}
