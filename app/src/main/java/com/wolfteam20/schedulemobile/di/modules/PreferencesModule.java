package com.wolfteam20.schedulemobile.di.modules;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelper;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.utils.Constants;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

@Module(includes = ContextModule.class)
public class PreferencesModule {

    @Provides
    public PreferencesHelperContract providePreferenceHelper(@ApplicationContext Context context) {
        return new PreferencesHelper(context, Constants.PREFERENCE_NAME);
    }
}
