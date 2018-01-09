package com.wolfteam20.schedulemobile.data.services;

import com.wolfteam20.schedulemobile.data.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Observable<Response<TokenDTO>> getToken(@Field("username") String username, @Field("password") String password, @Field("isMobile") Boolean isMobile);

    /**
     * Obtiene el periodo academico actual
     * @return PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera/Current")
    Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico();

    //Tiene que ser asi porque el guardado del archivo se debe hacer en otro thread
    @GET("api/HorarioProfesor/PlanificacionAcademica")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAcademica();

    @GET("api/HorarioProfesor/PlanificacionAulas")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAulas();

    @GET("api/HorarioProfesor/PlanificacionHorario")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionHorario();
}
