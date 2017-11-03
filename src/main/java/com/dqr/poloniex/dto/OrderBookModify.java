package com.dqr.poloniex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order Book Modify DTO.
 *
 * Ex. Data:
 * {"type":"orderBookModify","data":{"type":"ask","rate":"5769.70504797","amount":"0.00006504"}}
 *
 * Created by dqromney on 10/27/17.
 */
public final class OrderBookModify {
    public final String type;
    public final Data data;

    @JsonCreator
    public OrderBookModify(@JsonProperty("type") String type, @JsonProperty("data") Data data){
        this.type = type;
        this.data = data;
    }

    public static final class Data {
        public final String type;
        public final String rate;
        public final String amount;

        @JsonCreator
        public Data(@JsonProperty("type") String type, @JsonProperty("rate") String rate, @JsonProperty("amount") String amount){
            this.type = type;
            this.rate = rate;
            this.amount = amount;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Data{");
            sb.append("type='").append(type).append('\'');
            sb.append(", rate='").append(rate).append('\'');
            sb.append(", amount='").append(amount).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderBookModify{");
        sb.append("type='").append(type).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}