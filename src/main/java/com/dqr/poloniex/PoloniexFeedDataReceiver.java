package com.dqr.poloniex;

import com.dqr.IDataReceiver;
import com.dqr.poloniex.handler.PoloniexSubscription;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Crypto Currency Data Receiver (Poloniex)
 *
 * @see https://github.com/TheCookieLab/poloniex-api-java/blob/master/src/main/java/com/cf/example/PoloniexWSSClientExample.java
 */
@Log
public class PoloniexFeedDataReceiver implements IDataReceiver {

    final static CountDownLatch messageLatch = new CountDownLatch(1);

    private static final String ENDPOINT_URL = "wss://api.poloniex.com";
    private static final String DEFAULT_REALM = "realm1";

    private WSSClient poloniexWSSClient;

    public void init(String[] args) throws IOException {
        log.info("init()...");
    }

    public void execute() {
        log.info("execute()...");
        try {
            new PoloniexFeedDataReceiver().run();

        } catch (Exception e) {
            log.severe(String.format("An exception occurred when running PoloniexWSSClientExample - %1$s", e.getMessage()));
            System.exit(-1);
        }
    }

    public void run() throws Exception {
        try (WSSClient poloniexWSSClient = new WSSClient(ENDPOINT_URL, DEFAULT_REALM)) {
            poloniexWSSClient.subscribe(PoloniexSubscription.TICKER);
            poloniexWSSClient.subscribe(new PoloniexSubscription("trollbox"));
            // poloniexWSSClient.subscribe(new PoloniexSubscription("USDT_BTC"));
            // poloniexWSSClient.run(60000);
            poloniexWSSClient.run(180000);
        }
    }

    /*
        Oct 26, 2017 9:48:17 PM com.dqr.poloniex.WSSClient run
        INFO: WSSCient.run() : Enter
        Oct 26, 2017 9:48:17 PM com.dqr.poloniex.WSSClient lambda$run$0
        INFO: Disconnected
        Oct 26, 2017 9:48:17 PM com.dqr.poloniex.WSSClient lambda$run$0
        INFO: Connecting...
        Oct 26, 2017 9:48:51 PM com.dqr.poloniex.WSSClient lambda$run$0
        INFO: Connected
        Oct 26, 2017 9:49:06 PM com.dqr.poloniex.handler.PoloniexSubscription call
        WARNING: Exception processing event data - com.fasterxml.jackson.databind.node.ArrayNode cannot be cast to java.util.function.Supplier
     */
}
