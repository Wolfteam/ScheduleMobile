package com.wolfteam20.schedulemobile.di.components;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.network.ApiSchedule;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.di.modules.ApiScheduleModule;
import com.wolfteam20.schedulemobile.di.modules.DataManagerModule;
import com.wolfteam20.schedulemobile.di.modules.PreferencesModule;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import dagger.Component;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

@ApplicationScope
@Component(modules = {ApiScheduleModule.class, DataManagerModule.class, PreferencesModule.class})
public interface ApplicationComponent {
    void inject(App app);

    DataManagerContract getDatamanager();

    ApiSchedule getScheduleService();

    PreferencesHelperContract getPreferencesHelper();
}