package org.koroed.lepra;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.koroed.lepra.content.parser.LepraContentParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 14.05.14
 * Time: 13:10
 */
public class LepraHttpClient {
    BasicCookieStore cookieStore;
    CloseableHttpClient httpClient;
    HttpClientContext context;


    LepraHttpClient() {
        init();
    }

    private void init() {
        cookieStore = new BasicCookieStore();

        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
    }

    public <T> T loadContent(URI uri, LepraContentParser<T> parser) {
        return loadContent(uri, null, parser);
    }

    public <T> T loadContent(URI uri, Map<String, String> formAttributes, LepraContentParser<T> parser) {
        CloseableHttpResponse response = null;
        try {
            if (formAttributes != null && formAttributes.size() > 0) {
                response = httpClient.execute(createFormRequest(uri, formAttributes), context);
            } else {
                HttpGet httpget = new HttpGet(uri);
                response = httpClient.execute(httpget, context);
            }
            return parser.parseContent(response.getEntity().getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static HttpUriRequest createFormRequest(URI uri, Map<String, String> formAttributes) throws URISyntaxException {
        NameValuePair[] parameters = new NameValuePair[formAttributes.size()];
        String[] keys = new String[formAttributes.size()];
        formAttributes.keySet().toArray(keys);
        String[] values = new String[formAttributes.size()];
        formAttributes.values().toArray(values);
        for (int i = 0; i < formAttributes.size(); i++) {
            parameters[i] = new BasicNameValuePair(keys[i], values[i]);
        }
        return RequestBuilder.post()
                .setUri(uri)
                .addParameters(parameters)
                .build();
    }

}
