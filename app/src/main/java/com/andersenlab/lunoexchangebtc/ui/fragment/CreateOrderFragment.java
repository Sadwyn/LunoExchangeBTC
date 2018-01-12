package com.andersenlab.lunoexchangebtc.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.andersenlab.lunoexchangebtc.R;
import com.andersenlab.lunoexchangebtc.enums.CurrencyEnum;
import com.andersenlab.lunoexchangebtc.presentation.presenter.CreateOrderPresenter;
import com.andersenlab.lunoexchangebtc.presentation.view.CreateOrderView;
import com.andersenlab.lunoexchangebtc.ui.custom.DoubleToggleView;
import com.andersenlab.lunoexchangebtc.util.EnumUtils;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateOrderFragment extends BaseFragment implements CreateOrderView {
    public static final String TAG = "CreateOrderFragment";
    public static final String PAIR = "pair";
    public static final String PRICE = "price";
    public static final String VOLUME = "volume";
    public static final String TYPE = "type";

    public static final String BID = "BID";
    public static final String ASK = "ASK";
    public static final float ON_REQUEST_ONGOING_ALPHA = 0.5f;
    public static final int NO_ALPHA = 1;
    public static final int FRAGMENT_CLOSING_DELAY = 1000;
    @InjectPresenter
    CreateOrderPresenter mCreateOrderPresenter;
    @BindView(R.id.type_order_toggle)
    DoubleToggleView typeOrderToggle;
    @BindView(R.id.currencySpinner)
    Spinner currencySpinner;
    Unbinder unbinder;

    HashMap<String, String> postRequestMap;

    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.etVolume)
    EditText etVolume;
    @BindView(R.id.tvErrorMessage)
    TextView tvErrorMessage;
    @BindView(R.id.btnStartRequest)
    Button createOrderButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootLayout)
    ConstraintLayout rootLayout;
    @BindView(R.id.etKeyId)
    EditText etKeyId;
    @BindView(R.id.etKeySecret)
    EditText etKeySecret;


    public static CreateOrderFragment newInstance() {
        CreateOrderFragment fragment = new CreateOrderFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postRequestMap = new HashMap<>();
        initializeDoubleToggle();
        initializeSpinner();
    }

    private void initializeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                EnumUtils.getNames(CurrencyEnum.class));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }

    private void initializeDoubleToggle() {
        postRequestMap.put(TYPE, BID);
        typeOrderToggle.getLeftToggle()
                .setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_toggle_button_checked));

        typeOrderToggle.getLeftToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        typeOrderToggle.getRightToggle()
                .setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        typeOrderToggle.getLeftToggle().setOnClickListener(v -> {
            typeOrderToggle.getLeftToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            typeOrderToggle.getRightToggle().setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
            typeOrderToggle.getLeftToggle().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_toggle_button_checked));
            typeOrderToggle.getRightToggle().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_toggle_button_unchecked));
            postRequestMap.put(TYPE, BID);
        });
        typeOrderToggle.getRightToggle().setOnClickListener(v -> {
            typeOrderToggle.getLeftToggle().setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            typeOrderToggle.getRightToggle().setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            typeOrderToggle.getLeftToggle().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_toggle_button_unchecked));
            typeOrderToggle.getRightToggle().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_toggle_button_checked));
            postRequestMap.put(TYPE, ASK);
        });
    }

    @OnClick(R.id.btnStartRequest)
    public void onCreateOrderClick(View view) {
        tvErrorMessage.setText("");
        postRequestMap.put(PAIR, currencySpinner.getSelectedItem().toString());
        postRequestMap.put(PRICE, etPrice.getText().toString());
        postRequestMap.put(VOLUME, etVolume.getText().toString());
        setIsRequestOngoing(true);
        mCreateOrderPresenter.postLimitOrder(postRequestMap, etKeyId.getText().toString(), etKeySecret.getText().toString());
    }

    @Override
    public void onRequestError(String error) {
        setIsRequestOngoing(false);
        tvErrorMessage.setTextColor(Color.RED);
        tvErrorMessage.setText(error);
    }

    @Override
    public void onRequestSuccessful(int code) {
        setIsRequestOngoing(false);
        if(code == 200) {
            tvErrorMessage.setTextColor(Color.GREEN);
            tvErrorMessage.setText(R.string.success);
            handler.postDelayed(() -> getActivity().getSupportFragmentManager().popBackStack(), FRAGMENT_CLOSING_DELAY);
        }
    }

    private void setIsRequestOngoing(boolean isRequestOngoing) {
        progressBar.setVisibility(isRequestOngoing ? View.VISIBLE : View.GONE);
        for (int i = 0; i < rootLayout.getChildCount(); i++) {
            View child = rootLayout.getChildAt(i);
            child.setEnabled(!isRequestOngoing);
        }
        rootLayout.setAlpha(isRequestOngoing ? ON_REQUEST_ONGOING_ALPHA : NO_ALPHA);
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.create_new_order_toolbar_title);
    }

    @Override
    protected boolean isNeedShowToolbarBackArrow() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mCreateOrderPresenter.destroyView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCreateOrderPresenter.destroyView(this);
    }
}
