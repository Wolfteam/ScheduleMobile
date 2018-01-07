package com.wolfteam20.schedulemobile.di.modules;

import android.app.Activity;
import android.content.Context;

import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.qualifiers.ActivityContext;
import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.home.HomeContractPresenter;
import com.wolfteam20.schedulemobile.ui.home.HomeContractView;
import com.wolfteam20.schedulemobile.ui.home.HomePresenter;
import com.wolfteam20.schedulemobile.ui.login.LoginContractPresenter;
import com.wolfteam20.schedulemobile.ui.login.LoginContractView;
import com.wolfteam20.schedulemobile.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

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
    @ActivityScope
    LoginContractPresenter<LoginContractView> provideLoginPresenter(ScheduleService service, PreferencesHelperContract prefs) {
        return new LoginPresenter<>(service, prefs);
    }

    @Provides
    @ActivityScope
    HomeContractPresenter<HomeContractView> provideHomePresenter(HomePresenter<HomeContractView> presenter){
        return presenter;
    }
}