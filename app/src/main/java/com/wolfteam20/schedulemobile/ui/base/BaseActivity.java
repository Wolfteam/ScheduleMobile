package com.wolfteam20.schedulemobile.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;
//import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.utils.NetworkUtilities;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BaseActivity extends AppCompatActivity implements BaseContractView {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getApplication(this).getApplicationComponent())
                .build();
    }

    public ActivityComponent getActivityComponent(){
        return mActivityComponent;
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtilities.isNetworkAvailable(App.getApplication(this));
    }
}