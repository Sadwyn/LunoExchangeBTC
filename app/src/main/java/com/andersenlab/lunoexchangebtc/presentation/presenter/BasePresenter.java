package com.andersenlab.lunoexchangebtc.presentation.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import retrofit2.Call;


public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {
    protected Call call;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void destroyView(V view) {
        super.destroyView(view);
        if (call != null && !call.isCanceled())
            call.cancel();
    }
}
