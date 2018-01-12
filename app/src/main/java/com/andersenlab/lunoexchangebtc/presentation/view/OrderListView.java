package com.andersenlab.lunoexchangebtc.presentation.view;

import com.andersenlab.lunoexchangebtc.data.pojo.OrderBook;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface OrderListView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void onRequestError(String error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onRequestSuccessful(OrderBook orderBook);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSwipeOnScroll();
}
