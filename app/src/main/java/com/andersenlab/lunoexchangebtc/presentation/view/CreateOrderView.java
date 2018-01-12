package com.andersenlab.lunoexchangebtc.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface CreateOrderView extends MvpView {
    void onRequestError(String error);
    void onRequestSuccessful(int code);
}
