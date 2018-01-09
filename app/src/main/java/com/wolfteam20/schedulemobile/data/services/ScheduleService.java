package com.wolfteam20.schedulemobile.data.services;

import com.wolfteam20.schedulemobile.data.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public interface ScheduleService {

//    @GET("users/{user}/repos")
//    Call<List<String>> listRepos(@Path("user") String user);

    /**
     * Obtiene un token que contiene los privilegiso acorde al usuario
     * @param username Username
     * @param password Password
     * @return TokenDTO
     */
    @FormUrlEncoded
    @POST("token")
    Call<TokenDTO> getToken(@Field("username") String username, @Field("password") String password);

    /**
     * Obtiene el periodo academico actual
     * @return PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera/Current")
    Call<PeriodoAcademicoDTO> getCurrentPeriodoAcademico();

    @GET("api/HorarioProfesor/PlanificacionAcademica")
    @Streaming
    Call<ResponseBody> getPlanificacionAcademica(@Header("Authorization") String token);

    @GET("api/HorarioProfesor/PlanificacionAulas")
    @Streaming
    Call<ResponseBody> getPlanificacionAulas(@Header("Authorization") String token);

    @GET("api/HorarioProfesor/PlanificacionHorario")
    @Streaming
    Call<ResponseBody> getPlanificacionHorario(@Header("Authorization") String token);
}
