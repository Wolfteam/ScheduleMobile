package com.wolfteam20.schedulemobile.di.modules;

import com.wolfteam20.schedulemobile.data.db.DbHelper;
import com.wolfteam20.schedulemobile.data.db.DbHelperContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

@Module
public class DbHelperModule {
    @Provides
    DbHelperContract provideDbHelper(DbHelper dbHelper){
        return dbHelper;
    }
}
