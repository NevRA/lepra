package org.koroed.lepra.loader;

import org.koroed.lepra.LepraContext;
import org.koroed.lepra.LepraHttpClient;
import org.koroed.lepra.content.parser.LepraContentParser;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 28.05.2014
 * Time: 11:46
 */
public class LepraAsyncContentListLoader extends LepraContentListLoader {

    private ExecutorService executorService;
    private Future f;

    public LepraAsyncContentListLoader(URI uri, LepraContentParser<Integer> parser, String sorting, LepraContext ctx, LepraHttpClient httpClient) {
        super(uri, parser, sorting, ctx, httpClient);
    }

    public void suncLoad() {
        super.load();
    }

    public void load() {
        if(!hasNext()) {
            return;
        }
        stop();
        executorService = Executors.newSingleThreadExecutor();
        f = executorService.submit(new Runnable() {
            public void run() {
                suncLoad();

            }
        });
        executorService.shutdown();
    }

    public boolean isDone() {
        return f == null || f.isDone();
    }

    public void stop() {
        if(!isDone()) {
            f.cancel(true);
        }
        if(executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}
