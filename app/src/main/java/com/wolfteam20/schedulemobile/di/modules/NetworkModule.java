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
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ApplicationScope
    Cache provideHttpCache(File cacheFile) {
        long cacheSize = 10 * 1024 * 1024; //10mb cache
        return new Cache(cacheFile, cacheSize);
    }

    @Provides
    @ApplicationScope
    File provideCacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return interceptor;
    }
}
