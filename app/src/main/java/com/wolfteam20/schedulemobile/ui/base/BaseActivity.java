package com.wolfteam20.schedulemobile.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wolfteam20.schedulemobile.ScheduleApplication;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.modules.ActivityModule;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BaseActivity extends AppCompatActivity implements BaseContract.View {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .scheduleApplicationComponent(ScheduleApplication.getApplication(this).getApplicationComponent())
                .build();
    }

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    public ActivityComponent getActivityComponent(){
        return mActivityComponent;
    }
}