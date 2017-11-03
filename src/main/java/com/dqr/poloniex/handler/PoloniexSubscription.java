package com.dqr.poloniex.handler;

import com.dqr.poloniex.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.java.Log;
import rx.functions.Action1;
import ws.wamp.jawampa.PubSubData;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * @author David
 */
@Log
public class PoloniexSubscription implements Action1<PubSubData> {
    public static final PoloniexSubscription TICKER = new PoloniexSubscription("ticker");
    public static ObjectMapper objectMapper;

    // protected final static Logger LOG = LogManager.getLogger();
    public final String feedName;

    public PoloniexSubscription(String feedName) {
        this.feedName = feedName;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void call(PubSubData event) {
        String data = event.arguments().toString();
        try {
            // System.out.println(event.arguments().toString());
            ArrayNode arrayNode = event.arguments();
            // TODO Do the parsing here
            if (data.contains("type")) {
                Iterator<JsonNode> elementsIterator = arrayNode.iterator();
                while (elementsIterator.hasNext()) {
                    JsonNode node = elementsIterator.next();
                    if (node.toString().contains("orderBookRemove")) {
                        OrderBookRemove obr = parseOrderBookRemove(node.toString());
                        System.out.println(obr.toString());
                    }
                    if (node.toString().contains("orderBookModify")) {
                        OrderBookModify obm = parseOrderBookModify(node.toString());
                        System.out.println(obm.toString());
                    }
                    if (node.toString().contains("newTrade")) {
                        NewTrade nt = parseNewTrade(node.toString());
                        System.out.println(nt.toString());
                    }
                }
            } else {
                String preparedData = data.replace("[", "");
                preparedData = preparedData.replace("]", "");
                preparedData = preparedData.replace("\"", "");
                String fields[] = preparedData.split(",");

                if (data.contains("trollboxMessage")) {
                    Trollbox trollbox = populateTrollbox(fields);
                    System.out.println(trollbox.toString());

                } else {
                    Ticker ticker = populateTicker(fields);
                    System.out.println(ticker.toString());
                }
            }
        } catch (Exception ex) {
            log.warning("Exception processing event data - " + ex.getMessage());
        }
    }

    private Trollbox populateTrollbox(String[] fields) {
        Trollbox trollbox = new Trollbox();
        trollbox.setType(fields[0]);
        trollbox.setMessageNumber(new Long(fields[1]));
        trollbox.setUsername(fields[2]);
        trollbox.setMessage(fields[3]);
        trollbox.setReputation(new Integer(fields[4]));
        return trollbox;
    }

    private Ticker populateTicker(String[] fields) {
        Ticker ticker = new Ticker();

        ticker.setCurrencyPair(fields[0]);
        ticker.setLast(new BigDecimal(fields[1]));
        ticker.setLowestAsk(new BigDecimal(fields[2]));
        ticker.setHighestBid(new BigDecimal(fields[3]));
        ticker.setPercentChange(new BigDecimal(fields[4]));
        ticker.setBaseVolume(new BigDecimal(fields[5]));
        ticker.setQuoteVolume(new BigDecimal(fields[6]));
        ticker.setIsFrozen(new Boolean(fields[7]));
        ticker.setHigh24Hour(new BigDecimal(fields[8]));
        ticker.setLow24Hour(new BigDecimal(fields[9]));
        return ticker;
    }

    private OrderBookRemove parseOrderBookRemove(String json) throws IOException {
        OrderBookRemove obr = objectMapper.readValue(json, OrderBookRemove.class);
        return obr;
    }
    private OrderBookModify parseOrderBookModify(String json) throws IOException {
        OrderBookModify obm = objectMapper.readValue(json, OrderBookModify.class);
        return obm;
    }
    private NewTrade parseNewTrade(String json) throws IOException {
        NewTrade nt = objectMapper.readValue(json, NewTrade.class);
        return nt;
    }
    /*
        INFO: Connected
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_DCR","0.00512268","0.00512268","0.00507167","-0.11331358","95.96800923","18073.93029566",0,"0.00587679","0.00482999"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_GNO","0.01187037","0.01195442","0.01187149","-0.07407482","12.77447184","1038.41131205",0,"0.01302739","0.01178228"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BCH","342.09310762","341.99996000","340.99990006","0.03040092","2139412.97220669","6303.50620641",0,"350.66306724","327.11000059"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["ETH_OMG","0.02532029","0.02535416","0.02531668","-0.03480053","284.68433098","11049.25490907",0,"0.02654737","0.02510000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_OMNI","0.00336373","0.00333583","0.00330789","-0.08261742","27.37059601","8004.83998449",0,"0.00371923","0.00327937"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XCP","0.00138429","0.00139259","0.00138429","-0.05690752","9.76657124","6868.65997970",0,"0.00150600","0.00133610"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XMR","0.01463000","0.01468900","0.01463000","-0.05123155","535.27109139","35630.54491310",0,"0.01561527","0.01462500"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BTC","5933.66501890","5933.50000000","5928.00000001","0.02898899","24530638.70382375","4181.87868910",0,"5999.00201000","5669.10470001"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["XMR_DASH","3.21965429","3.24848021","3.20885593","-0.01582271","5.90171580","1.82943492",0,"3.26126052","3.20000000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["XMR_LTC","0.63360521","0.63360521","0.62913269","0.00521069","107.20037391","169.56152670",0,"0.64137983","0.61721321"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETH","0.05022099","0.05032046","0.05022099","-0.02668972","2621.93345441","51968.79117235",0,"0.05218245","0.04950000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_VTC","0.00065448","0.00065448","0.00065119","0.21781846","561.66345710","927035.05448888",0,"0.00066601","0.00052765"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XMR","0.01463000","0.01468899","0.01463000","-0.05123155","535.27109139","35630.54491310",0,"0.01561527","0.01462500"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BTC","5933.66501890","5933.49999997","5928.00000001","0.02898899","24530638.70382375","4181.87868910",0,"5999.00201000","5669.10470001"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETH","0.05022099","0.05032043","0.05022102","-0.02668972","2621.93345441","51968.79117235",0,"0.05218245","0.04950000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_ETH","297.16863000","297.79999995","297.16912003","-0.00111572","2880209.02355499","9724.33690623",0,"299.76246996","293.10000000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_SBD","0.00016430","0.00016832","0.00016430","-0.08813408","3.98999694","23288.27371988",0,"0.00018074","0.00016400"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETC","0.00175244","0.00176119","0.00175452","-0.03671333","290.91591193","164150.95168197",0,"0.00185000","0.00170648"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_BCH","0.05789930","0.05789844","0.05761503","0.00622651","915.35888201","15801.70444328",0,"0.06160030","0.05595312"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["ETH_OMG","0.02532029","0.02535416","0.02531669","-0.03480053","284.68433098","11049.25490907",0,"0.02654737","0.02510000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_EMC2","0.00001165","0.00001186","0.00001165","-0.07905138","48.93427815","3889804.14238406",0,"0.00001344","0.00001145"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_OMNI","0.00336373","0.00333575","0.00330789","-0.08261742","27.37059601","8004.83998449",0,"0.00371923","0.00327937"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_VTC","0.00065448","0.00065448","0.00065120","0.21781846","561.66345710","927035.05448888",0,"0.00066601","0.00052765"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XMR","0.01463000","0.01468900","0.01463000","-0.05123155","535.27109139","35630.54491310",0,"0.01561527","0.01462500"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_FCT","0.00253930","0.00253928","0.00250269","-0.03910119","104.89367236","41465.54896120",0,"0.00268153","0.00245007"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_BCH","0.05789930","0.05789843","0.05761503","0.00622651","915.35888201","15801.70444328",0,"0.06160030","0.05595312"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_CVC","0.00005460","0.00005476","0.00005462","0.04019813","62.17808612","1176843.21027019",0,"0.00005650","0.00005002"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_GAME","0.00033439","0.00033429","0.00033142","-0.11879726","131.49603111","360951.76845688",0,"0.00039800","0.00033120"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XMR","0.01463000","0.01468899","0.01463000","-0.05123155","535.27109139","35630.54491310",0,"0.01561527","0.01462500"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BTC","5933.66501890","5933.49999994","5928.00000001","0.02898899","24530638.70382375","4181.87868910",0,"5999.00201000","5669.10470001"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["XMR_DASH","3.21965429","3.24848021","3.20885595","-0.01582271","5.90171580","1.82943492",0,"3.26126052","3.20000000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETH","0.05022099","0.05032040","0.05022102","-0.02668972","2621.93345441","51968.79117235",0,"0.05218245","0.04950000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_EXP","0.00029089","0.00029331","0.00029067","-0.10138704","13.87315980","45397.12637401",0,"0.00033028","0.00028355"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_FCT","0.00253930","0.00253928","0.00250270","-0.03910119","104.89367236","41465.54896120",0,"0.00268153","0.00245007"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_DCR","0.00512268","0.00512268","0.00507168","-0.11331358","95.96800923","18073.93029566",0,"0.00587679","0.00482999"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETC","0.00175244","0.00176119","0.00175453","-0.03671333","290.91591193","164150.95168197",0,"0.00185000","0.00170648"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_ETC","10.37200937","10.41059645","10.35729240","-0.01218948","393477.49200118","37752.61847642",0,"10.65400632","10.18298635"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_STRAT","0.00071000","0.00071140","0.00071000","-0.08512228","960.42543486","1276469.28100388",0,"0.00086036","0.00065139"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BCH","342.09310762","341.99996000","340.99990007","0.03040092","2139412.97220669","6303.50620641",0,"350.66306724","327.11000059"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["ETH_OMG","0.02532029","0.02535416","0.02531670","-0.03480053","284.68433098","11049.25490907",0,"0.02654737","0.02510000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_XMR","0.01463000","0.01468896","0.01463000","-0.05123155","535.27109139","35630.54491310",0,"0.01561527","0.01462500"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_STR","0.03432312","0.03430817","0.03418323","-0.03510281","1156326.28482016","32777478.87833553",0,"0.03744246","0.03380000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["XMR_DASH","3.21965429","3.24848021","3.20885596","-0.01582271","5.90171580","1.82943492",0,"3.26126052","3.20000000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["XMR_LTC","0.63360521","0.63360521","0.62913270","0.00521069","107.20037391","169.56152670",0,"0.64137983","0.61721321"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["BTC_ETH","0.05022099","0.05032039","0.05022102","-0.02668972","2621.93345441","51968.79117235",0,"0.05218245","0.04950000"]
        Oct 26, 2017 9:53:41 PM com.dqr.poloniex.handler.PoloniexSubscription call
        INFO: ["USDT_BCH","342.09310762","341.99996000","340.99990008","0.03040092","2139412.97220669","6303.50620641",0,"350.66306724","327.11000059"]

     */
}