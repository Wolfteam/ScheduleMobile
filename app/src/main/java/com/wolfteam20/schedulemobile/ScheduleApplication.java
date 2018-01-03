package com.wolfteam20.schedulemobile;

import android.app.Activity;
import android.app.Application;

import com.wolfteam20.schedulemobile.di.components.DaggerScheduleApplicationComponent;
import com.wolfteam20.schedulemobile.di.components.ScheduleApplicationComponent;
import com.wolfteam20.schedulemobile.di.modules.ContextModule;


/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public class ScheduleApplication extends Application {

    private ScheduleApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerScheduleApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ScheduleApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }

    public static ScheduleApplication getApplication(Activity activity){
        return (ScheduleApplication) activity.getApplication();
    }
}