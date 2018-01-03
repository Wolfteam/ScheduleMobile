package com.wolfteam20.schedulemobile.di.modules;

import android.content.Context;


import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.di.scopes.ScheduleApplicationScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ScheduleApplicationScope
    public Cache provideHttpCache(File cacheFile) {
        long cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheFile, cacheSize);
    }

    @Provides
    @ScheduleApplicationScope
    public File provideCacheFile(@ApplicationContext Context context){
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @ScheduleApplicationScope
    public OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }
}
