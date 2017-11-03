package com.dqr.poloniex.handler;

import lombok.extern.java.Log;
import rx.functions.Action1;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * @author David
 */
@Log
public class PoloniexSubscriptionExceptionHandler implements Action1<Throwable> {
    // private final static Logger LOG = LogManager.getLogger();
    private final String name;

    public PoloniexSubscriptionExceptionHandler(String name) {
        this.name = name;
    }

    @Override
    public void call(Throwable t) {
        log.warning(String.format("%1$s handler encountered exception - %2$s", name, t.getMessage()));
    }

}