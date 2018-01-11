package com.wolfteam20.schedulemobile.di.modules;

import android.app.Application;
import android.content.Context;

import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module
public class ApplicationContextModule {

    private final Application mContext;

    public ApplicationContextModule(Application mContext) {
        this.mContext = mContext;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context provideContext() {
        return mContext;
    }
}
