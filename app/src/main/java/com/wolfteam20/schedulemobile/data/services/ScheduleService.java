package com.wolfteam20.schedulemobile.data.services;

import com.wolfteam20.schedulemobile.data.models.TokenDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public interface ScheduleService {

    @GET("users/{user}/repos")
    Call<List<String>> listRepos(@Path("user") String user);

    /**
     * Obtiene un token que contiene los privilegiso acorde al usuario
     * @param username Username
     * @param password Password
     * @return TokenDTO
     */
    @FormUrlEncoded
    @POST("token")
    Call<TokenDTO> getToken(@Field("username") String username, @Field("password") String password);
}
