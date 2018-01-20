package com.wolfteam20.schedulemobile.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wolfteam20.schedulemobile.di.components.ActivityComponent;

import butterknife.Unbinder;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public abstract class BaseFragment extends Fragment implements BaseViewContract {
    private BaseActivity mBaseActivity;
    private BaseDrawerActivity mBaseDrawerActivity;
    private Unbinder mUnBinder;

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

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        if (mBaseActivity != null)
            return mBaseActivity.getActivityComponent();
        else if (mBaseDrawerActivity != null)
            return mBaseDrawerActivity.getActivityComponent();
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public BaseDrawerActivity getBaseDrawerActivity() {
        return mBaseDrawerActivity;
    }


    @Override
    public void hideKeyboard() {
        if (mBaseActivity != null)
            mBaseActivity.hideKeyboard();
        else if (mBaseDrawerActivity != null)
            mBaseDrawerActivity.hideKeyboard();
    }

    /**
     * Metodo llamado luego del onViewCreated, aca se debe salvar la vista
     * al presenter, hacerle subscribe,etc
     *
     * @param view               View
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

    @Override
    public void openActivityOnTokenExpire() {
        if (mBaseActivity != null) {
            mBaseActivity.openActivityOnTokenExpire();
        }
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
}