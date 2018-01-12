package com.andersenlab.lunoexchangebtc.presentation.presenter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.andersenlab.lunoexchangebtc.data.pojo.OrderBook;
import com.andersenlab.lunoexchangebtc.enums.CurrencyEnum;
import com.andersenlab.lunoexchangebtc.network.Api;
import com.andersenlab.lunoexchangebtc.network.ServiceGenerator;
import com.andersenlab.lunoexchangebtc.presentation.view.OrderListView;
import com.arellomobile.mvp.InjectViewState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class OrderListPresenter extends BasePresenter<OrderListView> {
    private String lastPair;

    public String getLastPair() {
        return lastPair;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showSwipeOnScroll();
        startOrderBookQuery(CurrencyEnum.XBTZAR.name());
    }

    public void startOrderBookQuery(String pair) {
        lastPair = pair;
        call = ServiceGenerator.createService(Api.class).getOrderBook(pair);
        call.enqueue(new Callback<OrderBook>() {
            @Override
            public void onResponse(@NonNull Call<OrderBook> call, @NonNull Response<OrderBook> response) {
                Log.i("Retrofit", "Request Successfull");
                getViewState().onRequestSuccessful(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrderBook> call, @NonNull Throwable t) {
                Log.i("Retrofit", t.getMessage());
                if(t.getMessage()!=null)
                getViewState().onRequestError(t.getMessage());
            }
        });
    }
}
