package com.andersenlab.lunoexchangebtc.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andersenlab.lunoexchangebtc.R;
import com.andersenlab.lunoexchangebtc.data.pojo.Ask;
import com.andersenlab.lunoexchangebtc.data.pojo.Bid;
import com.andersenlab.lunoexchangebtc.data.pojo.OrderBook;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.TransactionViewHolder> {
    public static int BID_TYPE = 10;
    public static int ASK_TYPE = 20;
    private int currentType;
    private int listSize = 0;
    private List<Ask> askList = new ArrayList<>();
    private List<Bid> bidList = new ArrayList<>();

    public OrdersListAdapter() {
        currentType = BID_TYPE;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    public void setOrderBook(OrderBook orderBook) {
        askList = orderBook.getAsks();
        bidList = orderBook.getBids();
        listSize = currentType == BID_TYPE ? bidList.size() : askList.size();
        notifyDataSetChanged();
    }

    public void setItemType(int type) {
        currentType = type;
        listSize = type == BID_TYPE ? bidList.size() : askList.size();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        if (currentType == BID_TYPE) {
            Bid bid = bidList.get(holder.getAdapterPosition());
            holder.priceValueView.setText(bid.getPrice());
            holder.volumeValueView.setText(bid.getVolume());
        } else {
            Ask ask = askList.get(holder.getAdapterPosition());
            holder.priceValueView.setText(ask.getPrice());
            holder.volumeValueView.setText(ask.getVolume());
        }
    }

    @Override
    public int getItemCount() {
        return listSize;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.priceTextView)
        TextView priceTextView;
        @BindView(R.id.priceValueView)
        TextView priceValueView;
        @BindView(R.id.volumeTextView)
        TextView volumeTextView;
        @BindView(R.id.volumeValueView)
        TextView volumeValueView;

        TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
