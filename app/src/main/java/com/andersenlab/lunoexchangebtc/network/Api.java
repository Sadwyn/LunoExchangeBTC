package com.andersenlab.lunoexchangebtc.network;


import com.andersenlab.lunoexchangebtc.data.pojo.OrderBook;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("api/1/postorder")
    Call<Object> postOrder(@FieldMap Map<String, String> fields);

    @GET("api/1/listorders")
    Call<Object> getOrders();

    @GET("api/1/orderbook")
    Call<OrderBook> getOrderBook(@Query("pair") String pair);
}
