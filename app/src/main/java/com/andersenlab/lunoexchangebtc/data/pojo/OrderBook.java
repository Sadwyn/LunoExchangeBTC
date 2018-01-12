
package com.andersenlab.lunoexchangebtc.data.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderBook {

    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("bids")
    @Expose
    private List<Bid> bids = new ArrayList<>();
    @SerializedName("asks")
    @Expose
    private List<Ask> asks = new ArrayList<>();

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Ask> getAsks() {
        return asks;
    }

    public void setAsks(List<Ask> asks) {
        this.asks = asks;
    }

}
