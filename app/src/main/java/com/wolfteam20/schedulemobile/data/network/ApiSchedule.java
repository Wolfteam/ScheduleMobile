package com.wolfteam20.schedulemobile.data.network;

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public interface ApiSchedule {
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
     * Obtiene una lista con todos los profesores
     * @return List<ProfesorDetailsDTO>
     */
    @GET("api/Profesor")
    Observable<List<ProfesorDetailsDTO>> getAllProfesores();

    /**
     * Obtiene el periodo academico actual
     * @return PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera/Current")
    Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico();

    /**
     * Obtiene la disponibilidad de un profesor en particular
     * @param cedula Cedula del profesor
     * @return DisponibilidadDetailsDTO
     */
    @GET("api/Disponibilidad/{cedula}")
    Observable<DisponibilidadDetailsDTO> getDisponbilidad(@Path("cedula") int cedula);

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

    /**
     * Obtiene un profesor en particular
     * @param cedula Cedula del profesor a obtener
     * @return ProfesorDTO
     */
    @GET("api/Profesor/{cedula}")
    Observable<ProfesorDetailsDTO> getProfesor(@Path("cedula") int cedula);
}
