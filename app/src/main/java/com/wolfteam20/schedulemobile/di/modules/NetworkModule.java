package com.wolfteam20.schedulemobile.di.modules;

import android.content.Context;


import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

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
    @ApplicationScope
    Cache provideHttpCache(File cacheFile) {
        long cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheFile, cacheSize);
    }

    @Provides
    @ApplicationScope
    File provideCacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
