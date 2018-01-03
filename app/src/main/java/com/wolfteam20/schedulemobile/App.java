package com.wolfteam20.schedulemobile;

import android.app.Activity;
import android.app.Application;

import com.wolfteam20.schedulemobile.di.components.ApplicationComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerApplicationComponent;
import com.wolfteam20.schedulemobile.di.modules.ContextModule;


/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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