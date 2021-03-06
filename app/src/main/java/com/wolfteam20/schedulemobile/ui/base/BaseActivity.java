package com.wolfteam20.schedulemobile.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

//import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BaseActivity extends MvpAppCompatActivity implements BaseViewContract {

    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getApplication(this).getApplicationComponent())
                .build();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onError(String message) {
        if (message != null)
            showSnakBar(message);
        else
            showSnakBar(getString(R.string.error));
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getIntent(this));
        finish();
    }


    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showSuccessMessage(@NotNull String message) {
        Toasty.success(this, message).show();
    }

    @Override
    public void showSuccessMessage(int resId) {
        showSuccessMessage(getResources().getString(resId));
    }

    @Override
    public void showInfoMessage(@NotNull String message) {
        Toasty.info(this, message).show();
    }

    @Override
    public void showInfoMessage(int resId) {
        showInfoMessage(getResources().getString(resId));
    }

    @Override
    public void showWarningMessage(@NotNull String message) {
        Toasty.warning(this, message).show();
    }

    @Override
    public void showWarningMessage(int resId) {
        showWarningMessage(getResources().getString(resId));
    }

    @Override
    public void showErrorMessage(@NotNull String message) {
        Toasty.error(this, message).show();
    }

    @Override
    public void showErrorMessage(int resId) {
        showMessage(getResources().getString(resId));
    }

    private void showSnakBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }
}