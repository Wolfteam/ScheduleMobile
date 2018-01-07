package com.wolfteam20.schedulemobile.di.components;

import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.home.HomeFragment;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;

import dagger.Component;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(HomeFragment homeFragment);
}