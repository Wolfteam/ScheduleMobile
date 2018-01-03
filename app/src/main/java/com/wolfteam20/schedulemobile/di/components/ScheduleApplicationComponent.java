package com.wolfteam20.schedulemobile.di.components;


import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.modules.ScheduleServiceModule;
import com.wolfteam20.schedulemobile.di.scopes.ScheduleApplicationScope;

import dagger.Component;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

@ScheduleApplicationScope
@Component(modules = ScheduleServiceModule.class)
public interface ScheduleApplicationComponent {
    ScheduleService getScheduleService();
}
