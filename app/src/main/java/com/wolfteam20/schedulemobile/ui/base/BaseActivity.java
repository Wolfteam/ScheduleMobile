package com.wolfteam20.schedulemobile.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;
import com.wolfteam20.schedulemobile.utils.NetworkUtilities;

import butterknife.Unbinder;

//import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BaseActivity extends AppCompatActivity implements BaseContractView {

    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getApplication(this).getApplicationComponent())
                .build();
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent(){
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
    public boolean isNetworkAvailable() {
        return NetworkUtilities.isNetworkAvailable(App.getApplication(this));
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getIntent(this));
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
}