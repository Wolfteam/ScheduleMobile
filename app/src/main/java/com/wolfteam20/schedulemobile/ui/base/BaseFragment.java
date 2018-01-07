package com.wolfteam20.schedulemobile.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wolfteam20.schedulemobile.di.components.ActivityComponent;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public abstract class BaseFragment extends Fragment implements BaseContractView {
    private BaseActivity mBaseActivity;
    private BaseDrawerActivity mBaseDrawerActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity)
            mBaseActivity = (BaseActivity) context;
        else if (context instanceof BaseDrawerActivity)
            mBaseDrawerActivity = (BaseDrawerActivity) context;
    }

    @Override
    public void onDetach() {
        mBaseActivity = null;
        mBaseDrawerActivity = null;
        super.onDetach();
    }

    public ActivityComponent getActivityComponent(){
        if (mBaseActivity != null)
            return mBaseActivity.getActivityComponent();
        else if (mBaseDrawerActivity != null)
            return mBaseDrawerActivity.getActivityComponent();
        return null;
    }

    public BaseActivity getBaseActivity(){
        return mBaseActivity;
    }

    public BaseDrawerActivity getBaseDrawerActivity(){
        return mBaseDrawerActivity;
    }

    /**
     * Metodo llamado luego del onViewCreated, aca se debe salvar la vista
     * al presenter, hacerle subscribe,etc
     * @param view View
     * @param savedInstanceState Bundle
     */
    protected abstract void initLayout(View view, Bundle savedInstanceState);

    @Override
    public boolean isNetworkAvailable() {
        if (mBaseActivity != null)
            return mBaseActivity.isNetworkAvailable();
        else
            return mBaseDrawerActivity.isNetworkAvailable();
    }
}