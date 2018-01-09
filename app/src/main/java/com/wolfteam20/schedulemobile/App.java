package com.wolfteam20.schedulemobile;

import android.app.Activity;
import android.app.Application;

import com.wolfteam20.schedulemobile.di.components.ApplicationComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerApplicationComponent;
import com.wolfteam20.schedulemobile.di.modules.ContextModule;

import timber.log.Timber;


/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }

    public static App getApplication(Activity activity){
        return (App) activity.getApplication();
    }
}