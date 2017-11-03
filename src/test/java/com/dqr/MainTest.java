package com.dqr;

import com.dqr.poloniex.dto.NewTrade;
import com.dqr.poloniex.dto.OrderBookModify;
import com.dqr.poloniex.dto.OrderBookRemove;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainTest extends TestCase{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MainTest.class);
    }

    public void testParseNewTradeData() throws IOException {
         String newTradeJson = "{\"type\":\"newTrade\",\"data\":{\"amount\":\"0.03591039\",\"date\":\"2017-10-14 11:33:51\",\"rate\":\"5737.80305380\",\"total\":\"206.04674540\",\"tradeID\":\"9368327\",\"type\":\"buy\"}}";
         ObjectMapper mapper = new ObjectMapper();

         NewTrade readValue = mapper.readValue(newTradeJson, NewTrade.class);

         assertNotNull(readValue);
         assertThat(readValue.type, equalTo("newTrade"));
         assertNotNull(readValue.data);

     }

     public void testParseOrderBookModifyData() throws IOException {
         String newTradeJson = "{\"type\":\"orderBookModify\",\"data\":{\"type\":\"ask\",\"rate\":\"5769.70504797\",\"amount\":\"0.00006504\"}}";
         ObjectMapper mapper = new ObjectMapper();

         OrderBookModify readValue = mapper.readValue(newTradeJson, OrderBookModify.class);

         assertNotNull(readValue);
         assertThat(readValue.type, equalTo("orderBookModify"));
         assertNotNull(readValue.data);

     }

     public void testParseOrderBookRemoveData() throws IOException {
         String newTradeJson = "{\"type\":\"orderBookRemove\",\"data\":{\"type\":\"ask\",\"rate\":\"5749.84349317\"}}";
         ObjectMapper mapper = new ObjectMapper();

         OrderBookRemove readValue = mapper.readValue(newTradeJson, OrderBookRemove.class);

         assertNotNull(readValue);
         assertThat(readValue.type, equalTo("orderBookRemove"));
         assertNotNull(readValue.data);

     }

}
