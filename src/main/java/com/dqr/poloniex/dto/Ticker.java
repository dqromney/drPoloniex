package com.dqr.poloniex.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Ticker DTO.
 *
 * Labels:
 * currencyPair, last, lowestAsk, highestBid, percentChange, baseVolume, quoteVolume, isFrozen, 24hrHigh, 24hrLow
 *
 * Ex. Data:
 * ["USDT_ZEC","234.55700000","234.65800000","234.45898752","-0.03075619","883899.20661011","3716.48972692",0,"245.00400000","231.00000000"]
 *
 * Created by dqromney on 10/28/17.
 */
@Data
public class Ticker {

    String currencyPair;
    BigDecimal last;
    BigDecimal lowestAsk;
    BigDecimal highestBid;
    BigDecimal percentChange;
    BigDecimal baseVolume;
    BigDecimal quoteVolume;
    Boolean isFrozen;
    BigDecimal high24Hour;
    BigDecimal low24Hour;

}
