package com.wolfteam20.schedulemobile.di.components;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.modules.DataManagerModule;
import com.wolfteam20.schedulemobile.di.modules.PreferencesModule;
import com.wolfteam20.schedulemobile.di.modules.ScheduleServiceModule;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import dagger.Component;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

@ApplicationScope
@Component(modules = {DataManagerModule.class ,PreferencesModule.class, ScheduleServiceModule.class})
public interface ApplicationComponent {
    void inject(App app);

    DataManagerContract getDatamanager();

    ScheduleService getScheduleService();

    PreferencesHelperContract getPreferencesHelper();
}