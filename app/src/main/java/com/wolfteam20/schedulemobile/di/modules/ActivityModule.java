package com.wolfteam20.schedulemobile.di.modules;

import android.app.Activity;
import android.content.Context;

import com.wolfteam20.schedulemobile.di.qualifiers.ActivityContext;
import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    @ActivityScope
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }
    
//    @Provides
//    @ActivityScope
//    LoginPresenterContract provideLoginPresenter(LoginPresenter presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @ActivityScope
//    HomePresenterContract provideHomePresenter(HomePresenter presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @ActivityScope
//    DispPresenterContract provideDisponibilidadPresenter(DispPresenter presenter){
//        return presenter;
//    }
//
//    @Provides
//    @ActivityScope
//    DispDetailsPresenterContract provideDisponibilidadDetails(DispDetailsPresenter presenter){
//        return presenter;
//    }
}