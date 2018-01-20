package com.wolfteam20.schedulemobile.di.modules;

import android.app.Activity;
import android.content.Context;

import com.wolfteam20.schedulemobile.di.qualifiers.ActivityContext;
import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.disponibilidad.DispPresenterContract;
import com.wolfteam20.schedulemobile.ui.disponibilidad.DispViewContract;
import com.wolfteam20.schedulemobile.ui.disponibilidad.DispPresenter;
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsPresenterContract;
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsViewContract;
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsPresenter;
import com.wolfteam20.schedulemobile.ui.home.HomePresenterContract;
import com.wolfteam20.schedulemobile.ui.home.HomeViewContract;
import com.wolfteam20.schedulemobile.ui.home.HomePresenter;
import com.wolfteam20.schedulemobile.ui.login.LoginPresenterContract;
import com.wolfteam20.schedulemobile.ui.login.LoginViewContract;
import com.wolfteam20.schedulemobile.ui.login.LoginPresenter;

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
    
    @Provides
    @ActivityScope
    LoginPresenterContract<LoginViewContract> provideLoginPresenter(LoginPresenter<LoginViewContract> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    HomePresenterContract<HomeViewContract> provideHomePresenter(HomePresenter<HomeViewContract> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    DispPresenterContract<DispViewContract> provideDisponibilidadPresenter(DispPresenter<DispViewContract> presenter){
        return presenter;
    }

    @Provides
    @ActivityScope
    DispDetailsPresenterContract<DispDetailsViewContract> provideDisponibilidadDetails(DispDetailsPresenter<DispDetailsViewContract> presenter){
        return presenter;
    }
}