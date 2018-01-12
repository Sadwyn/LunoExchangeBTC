package com.andersenlab.lunoexchangebtc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.*;

import com.andersenlab.lunoexchangebtc.R;
import com.andersenlab.lunoexchangebtc.interfaces.ToolbarListener;
import com.andersenlab.lunoexchangebtc.presentation.presenter.MainPresenter;
import com.andersenlab.lunoexchangebtc.presentation.view.MainView;
import com.andersenlab.lunoexchangebtc.ui.fragment.OrdersListFragment;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, ToolbarListener {
    public static final String TAG = "MainActivity";
    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) == null) {
            replaceFragment(OrdersListFragment.newInstance(), false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void replaceFragment(Fragment fragment, boolean shouldAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out);
        transaction.replace(R.id.fragmentContainer, fragment);
        if (shouldAddToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed();
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void showToolbarBackArrow(boolean isNeedShow) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isNeedShow);
        }
    }
}
