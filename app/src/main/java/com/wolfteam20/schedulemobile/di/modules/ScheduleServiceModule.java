package com.wolfteam20.schedulemobile.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wolfteam20.schedulemobile.BuildConfig;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module(includes = NetworkModule.class)
public class ScheduleServiceModule {

    @Provides
    @ApplicationScope
    ScheduleService provideScheduleService(Retrofit retrofit) {
        return retrofit.create(ScheduleService.class);
    }

    @Provides
    @ApplicationScope
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return gsonBuilder.create();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.URLBaseAPI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}