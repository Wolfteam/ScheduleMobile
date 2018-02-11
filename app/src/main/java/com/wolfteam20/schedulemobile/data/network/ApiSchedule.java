package com.wolfteam20.schedulemobile.data.network;

import com.wolfteam20.schedulemobile.data.network.models.AulaDTO;
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.SeccionesDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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
     * Obtiene el periodo academico actual
     * @return PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera/Current")
    Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico();

    /**
     * Obtiene la planificacion academica
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionAcademica")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAcademica();

    /**
     * Obtiene la planificacion por aulas
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionAulas")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAulas();

    /**
     * Obtiene la planificacion por horarios
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionHorario")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionHorario();

    /**
     * Obtiene una lista con todos los profesores
     * @return List<ProfesorDetailsDTO>
     */
    @GET("api/Profesor")
    Observable<List<ProfesorDetailsDTO>> getAllProfesores();

    /**
     * Obtiene un profesor en particular
     * @param cedula Cedula del profesor a obtener
     * @return ProfesorDTO
     */
    @GET("api/Profesor/{cedula}")
    Observable<ProfesorDetailsDTO> getProfesor(@Path("cedula") int cedula);

    /**
     * Obtiene la disponibilidad de un profesor en particular
     * @param cedula Cedula del profesor
     * @return DisponibilidadDetailsDTO
     */
    @GET("api/Disponibilidad/{cedula}")
    Observable<DisponibilidadDetailsDTO> getDisponbilidad(@Path("cedula") int cedula);

    /**
     * Guarda la disponibilidades pasadas por parametro
     * y reescribe las existentes
     * @param disponibilidades Disponibilidades a guardar
     * @return ResponseBody
     */
    @POST("api/Disponibilidad")
    Observable<Response<ResponseBody>> addDisponibilidad(@Body List<DisponibilidadDTO> disponibilidades);

    /**
     * Obtiene una lista con todas las aulas
     * @return List de AulaDetailsDTO
     */
    @GET("api/Aulas")
    Observable<List<AulaDetailsDTO>> getAllAulas();

    /**
     * Remueve varias aulas
     * @param idAulas Id de las aulas a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Aulas")
    Completable removeAulas(@Query("idAulas") String idAulas);

    /**
     * Remueve una aula en particular
     * @param idAula Id del aula a remover
     * @return Completable
     */
    @DELETE("api/Aulas/{idAula}")
    Completable removeAula(@Path("idAula") long idAula);

    /**
     * Agrega un aula en particular
     * @param aula Aula a crear
     * @return Completable
     */
    @POST("api/Aulas")
    Completable addAula(@Body AulaDTO aula);

    /**
     * Actualiza un aula en particular
     * @param aula Aula a crear
     * @return Completable
     */
    @POST("api/Aulas")
    Completable updateAula(@Body AulaDTO aula);

    /**
     * Obtiene una lista con todas las materias
     * @return List de MateriaDetailsDTO
     */
    @GET("api/Materias")
    Observable<List<MateriaDetailsDTO>> getAllMaterias();

    /**
     * Obtiene una lista con todos los periodos academicos creados
     * @return List de PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera")
    Observable<List<PeriodoAcademicoDTO>> getAllPeriodosAcademicos();

    /**
     * Obtiene una lista de relaciones profesor-materia
     * @return List de ProfesorMateriaDetailsDTO
     */
    @GET("api/ProfesorMateria")
    Observable<List<ProfesorMateriaDetailsDTO>> getAllProfesorMateria();

    /**
     * Obtiene una lista de las secciones acorde al periodo academico activo
     * @return List de SeccionesDetailsDTO
     */
    @GET("api/Secciones")
    Observable<List<SeccionesDetailsDTO>> getAllSecciones();

    /**
     * Obtiene una lista con todos los usuarios creados.
     * @return List de UsuarioDetailsDTO
     */
    @GET("api/Account")
    Observable<List<UsuarioDetailsDTO>> getAllUsuarios();
}
