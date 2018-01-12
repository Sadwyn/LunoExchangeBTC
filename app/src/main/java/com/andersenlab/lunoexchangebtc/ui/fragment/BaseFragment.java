package com.andersenlab.lunoexchangebtc.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.andersenlab.lunoexchangebtc.interfaces.ToolbarListener;
import com.andersenlab.lunoexchangebtc.ui.activity.MainActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;

public class BaseFragment extends MvpAppCompatFragment{
    protected MainActivity mainActivity;
    protected ToolbarListener toolbarListener;
    protected Handler handler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ToolbarListener)
            toolbarListener = (ToolbarListener)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        toolbarListener.setToolbarTitle(getToolbarTitle());
        toolbarListener.showToolbarBackArrow(isNeedShowToolbarBackArrow());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
    }

    protected String getToolbarTitle(){
        return "";
    }

    protected boolean isNeedShowToolbarBackArrow(){
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
