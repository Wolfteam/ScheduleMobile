package com.wolfteam20.schedulemobile.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.scopes.ScheduleApplicationScope;

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
    @ScheduleApplicationScope
    public ScheduleService provideScheduleService(Retrofit retrofit){
        return retrofit.create(ScheduleService.class);
    }

    @Provides
    @ScheduleApplicationScope
    public Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @ScheduleApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
