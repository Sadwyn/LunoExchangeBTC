package com.andersenlab.lunoexchangebtc.presentation.presenter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.andersenlab.lunoexchangebtc.network.Api;
import com.andersenlab.lunoexchangebtc.network.ServiceGenerator;
import com.andersenlab.lunoexchangebtc.presentation.view.CreateOrderView;
import com.arellomobile.mvp.InjectViewState;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CreateOrderPresenter extends BasePresenter<CreateOrderView> {

    public void postLimitOrder(Map<String, String> hashMap, String login, String password) {
       call = ServiceGenerator.createService(Api.class, login, password).postOrder(hashMap);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                getViewState().onRequestSuccessful(response.code());
                showErrorIfNeed(response);
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    getViewState().onRequestError(t.getMessage());
            }
        });
    }

    private void showErrorIfNeed(Response<Object> response) {
        if (response.errorBody() != null) {
            try {
                String error = response.errorBody().string();
                if (error != null)
                    getViewState().onRequestError(error);
            } catch (IOException e) {
                Log.e("IOEXCEPTION", "READ_ERROR_EXCEPTION", e);
            }
        }
    }
}
