package com.wolfteam20.schedulemobile;

import android.app.Activity;
import android.app.Application;

import com.wolfteam20.schedulemobile.data.network.models.MyObjectBox;
import com.wolfteam20.schedulemobile.di.components.ApplicationComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerApplicationComponent;
import com.wolfteam20.schedulemobile.di.modules.ApplicationContextModule;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import timber.log.Timber;


/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }
        Timber.d("Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }

    public static App getApplication(Activity activity){
        return (App) activity.getApplication();
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}