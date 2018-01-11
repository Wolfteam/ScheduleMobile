package com.wolfteam20.schedulemobile.di.modules;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module(includes = {ApplicationContextModule.class, PreferencesModule.class})
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
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, PreferencesHelperContract prefs) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    String token = prefs.getToken();
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }
}
