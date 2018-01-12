package com.andersenlab.lunoexchangebtc.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andersenlab.lunoexchangebtc.R;
import com.andersenlab.lunoexchangebtc.data.pojo.OrderBook;
import com.andersenlab.lunoexchangebtc.enums.CurrencyEnum;
import com.andersenlab.lunoexchangebtc.presentation.presenter.OrderListPresenter;
import com.andersenlab.lunoexchangebtc.presentation.view.OrderListView;
import com.andersenlab.lunoexchangebtc.ui.adapter.OrdersListAdapter;
import com.andersenlab.lunoexchangebtc.ui.custom.DoubleToggleView;
import com.andersenlab.lunoexchangebtc.util.EnumUtils;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrdersListFragment extends BaseFragment implements OrderListView {
    public static final String TAG = "TransactionListFragment";
    @InjectPresenter
    OrderListPresenter mOrderListPresenter;

    @BindView(R.id.listTransactionsRecycler)
    RecyclerView rvListTransactions;
    @BindView(R.id.createOrderButton)
    FloatingActionButton createOrder;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout transactionsLayout;
    @BindView(R.id.content_type_bar)
    DoubleToggleView contentTypeBar;
    Unbinder unbinder;

    OrdersListAdapter adapter;

    public static OrdersListFragment newInstance() {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        mOrderListPresenter.attachView(this);
    }

    private void initializeViews() {
        createOrder.setOnClickListener(v -> mainActivity.replaceFragment(CreateOrderFragment.newInstance(),
                true));
        transactionsLayout.setOnRefreshListener(() -> mOrderListPresenter.startOrderBookQuery(mOrderListPresenter.getLastPair()));
        adapter = new OrdersListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListTransactions.setLayoutManager(linearLayoutManager);
        rvListTransactions.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvListTransactions.setAdapter(adapter);

        contentTypeBar.getLeftToggle()
                .setBackground(ContextCompat.getDrawable(getContext(), R.color.black));
        contentTypeBar.getRightToggle()
                .setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        contentTypeBar.getLeftToggle().setOnClickListener(v -> {
            contentTypeBar.getLeftToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            contentTypeBar.getRightToggle().setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
            contentTypeBar.getLeftToggle().setBackground(ContextCompat.getDrawable(getContext(), R.color.black));
            contentTypeBar.getRightToggle().setBackground(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
            adapter.setItemType(OrdersListAdapter.BID_TYPE);
        });

        contentTypeBar.getRightToggle().setOnClickListener(v -> {
            contentTypeBar.getLeftToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            contentTypeBar.getRightToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            contentTypeBar.getLeftToggle().setBackground(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
            contentTypeBar.getRightToggle().setBackground(ContextCompat.getDrawable(getContext(), R.color.black));
            adapter.setItemType(OrdersListAdapter.ASK_TYPE);
        });
    }


    @Override
    public void onRequestError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        if (transactionsLayout.isRefreshing()) {
            transactionsLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRequestSuccessful(OrderBook orderBook) {
        if (transactionsLayout.isRefreshing()) {
            transactionsLayout.setRefreshing(false);
        }
        if (orderBook != null)
            adapter.setOrderBook(orderBook);
    }

    @Override
    public void showSwipeOnScroll() {
        if(!transactionsLayout.isRefreshing()){
            transactionsLayout.setRefreshing(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (String currency : EnumUtils.getNames(CurrencyEnum.class)) {
            menu.add(currency);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!transactionsLayout.isRefreshing())
            transactionsLayout.setRefreshing(true);
        mOrderListPresenter.startOrderBookQuery(item.getTitle().toString());
        return true;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.transaction_list_toolbar_title);
    }

    @Override
    protected boolean isNeedShowToolbarBackArrow() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOrderListPresenter.detachView(this);
    }
}
