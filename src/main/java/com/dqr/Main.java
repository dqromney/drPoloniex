package com.dqr;

import com.dqr.poloniex.PoloniexFeedDataReceiver;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class Main {
    public static void main(String[] args) throws IOException {
        PoloniexFeedDataReceiver pDr = new PoloniexFeedDataReceiver();
        pDr.init(args);
        pDr.execute();
    }
}
