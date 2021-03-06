package com.wolfteam20.schedulemobile.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;

import org.jetbrains.annotations.NotNull;

import butterknife.Unbinder;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public abstract class BaseFragment extends MvpAppCompatFragment implements BaseViewContract {
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

    public BaseViewContract getCurrentActivityContext() {
        if (getBaseActivity() != null)
            return getBaseActivity();
        else
            return getBaseDrawerActivity();
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
    public void onError(String message) {
        if (message != null)
            getCurrentActivityContext().onError(message);
        else
            getCurrentActivityContext().onError(R.string.error);
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
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

    @Override
    public void showMessage(String message) {
        getCurrentActivityContext().showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showSuccessMessage(@NotNull String message) {
        getCurrentActivityContext().showSuccessMessage(message);
    }

    @Override
    public void showSuccessMessage(int resId) {
        showSuccessMessage(getResources().getString(resId));
    }

    @Override
    public void showInfoMessage(@NotNull String message) {
        getCurrentActivityContext().showInfoMessage(message);
    }

    @Override
    public void showInfoMessage(int resId) {
        showInfoMessage(getResources().getString(resId));
    }

    @Override
    public void showWarningMessage(@NotNull String message) {
        getCurrentActivityContext().showWarningMessage(message);
    }

    @Override
    public void showWarningMessage(int resId) {
        showWarningMessage(getResources().getString(resId));
    }

    @Override
    public void showErrorMessage(@NotNull String message) {
        getCurrentActivityContext().showErrorMessage(message);
    }

    @Override
    public void showErrorMessage(int resId) {
        showMessage(getResources().getString(resId));
    }
}