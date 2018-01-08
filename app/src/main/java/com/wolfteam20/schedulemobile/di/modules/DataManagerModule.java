package com.wolfteam20.schedulemobile.di.modules;

import com.wolfteam20.schedulemobile.data.DataManager;
import com.wolfteam20.schedulemobile.data.DataManagerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efrain.Bastidas on 1/8/2018.
 */

@Module
public class DataManagerModule {
    @Provides
    DataManagerContract provideDataManager(DataManager manager){
        return manager;
    }
}
