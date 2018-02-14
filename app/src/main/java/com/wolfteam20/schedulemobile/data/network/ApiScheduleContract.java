package com.wolfteam20.schedulemobile.data.network;

import com.wolfteam20.schedulemobile.data.network.models.AulaDTO;
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.CarreraDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.PrivilegioDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.SeccionDTO;
import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.SemestreDTO;
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDTO;
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */

public interface ApiScheduleContract {

    /**
     * Obtiene un token que contiene los privilegiso acorde al usuario
     *
     * @param username Username
     * @param password Password
     * @return TokenDTO
     */
    @FormUrlEncoded
    @POST("token")
    Observable<Response<TokenDTO>> getToken(@Field("username") String username, @Field("password") String password, @Field("isMobile") Boolean isMobile);

    /**
     * Obtiene el periodo academico actual
     *
     * @return PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera/Current")
    Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico();

    /**
     * Obtiene la planificacion academica
     *
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionAcademica")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAcademica();

    /**
     * Obtiene la planificacion por aulas
     *
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionAulas")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionAulas();

    /**
     * Obtiene la planificacion por horarios
     *
     * @return ResponseBody Que contiene los bits del archivo a guardar
     */
    @GET("api/HorarioProfesor/PlanificacionHorario")
    @Streaming
    Observable<Response<ResponseBody>> getPlanificacionHorario();

    /**
     * Obtiene una lista con todos los profesores
     *
     * @return List<ProfesorDetailsDTO>
     */
    @GET("api/Profesor")
    Observable<List<ProfesorDetailsDTO>> getAllProfesores();

    /**
     * Obtiene un profesor en particular
     *
     * @param cedula Cedula del profesor a obtener
     * @return ProfesorDTO
     */
    @GET("api/Profesor/{cedula}")
    Observable<ProfesorDetailsDTO> getProfesor(@Path("cedula") int cedula);

    /**
     * Obtiene la disponibilidad de un profesor en particular
     *
     * @param cedula Cedula del profesor
     * @return DisponibilidadDetailsDTO
     */
    @GET("api/Disponibilidad/{cedula}")
    Observable<DisponibilidadDetailsDTO> getDisponbilidad(@Path("cedula") int cedula);

    /**
     * Guarda la disponibilidades pasadas por parametro
     * y reescribe las existentes
     *
     * @param disponibilidades Disponibilidades a guardar
     * @return ResponseBody
     */
    @POST("api/Disponibilidad")
    Observable<Response<ResponseBody>> addDisponibilidad(@Body List<DisponibilidadDTO> disponibilidades);

    /**
     * Obtiene todas las carreras que se cursan en la uni existentes
     * @return  Single de List<CarreraDTO>
     */
    @GET("api/Carreras")
    Single<List<CarreraDTO>> getAllCarreras();

    /**
     * Obtiene todas las tipos de aula-materia existentes
     * @return Single de List<TipoAulaMateriaDTO>
     */
    @GET("api/TipoAulaMateria")
    Single<List<TipoAulaMateriaDTO>> getAllTipoAulaMateria();

    /**
     * Obtiene todos los privilegios que puede tener un usuario (1 solo a la vez)
     * @return  Single de List<PrivilegioDTO>
     */
    @GET("api/Privilegios")
    Single<List<PrivilegioDTO>> getAllPrivilegios();

    /**
     * Obtiene todos los semestres a los que puede pertenecer una materia (1 solo a la vez)
     * @return Single de List<SemestreDTO>
     */
    @GET("api/Semestres")
    Single<List<SemestreDTO>> getAllSemestres();

    /**
     * Obtiene una lista con todas las aulas
     *
     * @return List de AulaDetailsDTO
     */
    @GET("api/Aulas")
    Observable<List<AulaDetailsDTO>> getAllAulas();

    /**
     * Remueve varias aulas
     *
     * @param idAulas Id de las aulas a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Aulas")
    Completable removeAulas(@Query("idAulas") @NotNull String idAulas);

    /**
     * Remueve una aula en particular
     *
     * @param idAula Id del aula a remover
     * @return Completable
     */
    @DELETE("api/Aulas/{idAula}")
    Completable removeAula(@Path("idAula") long idAula);

    /**
     * Agrega un aula en particular
     *
     * @param aula Aula a crear
     * @return Completable
     */
    @POST("api/Aulas")
    Completable addAula(@Body @NotNull AulaDTO aula);

    /**
     * Actualiza un aula en particular
     *
     * @param idAula Id del aula a actualizar
     * @param aula   Aula a actualizar
     * @return Completable
     */
    @PUT("api/Aulas/{idAula}")
    Completable updateAula(@Path("idAula") long idAula, @Body @NotNull AulaDTO aula);

    /**
     * Obtiene una lista con todas las materias
     *
     * @return List de MateriaDetailsDTO
     */
    @GET("api/Materias")
    Observable<List<MateriaDetailsDTO>> getAllMaterias();

    /**
     * Remueve varias materias
     *
     * @param codigos Codigos de las materias a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Materias")
    Completable removeMaterias(@Query("codigos") @NotNull String codigos);

    /**
     * Remueve una materia en particular
     *
     * @param codigo Codigo de la materia a remover
     * @return Completable
     */
    @DELETE("api/Materias/{codigo}")
    Completable removeMateria(@Path("codigo") long codigo);

    /**
     * Agrega una materia en particular
     *
     * @param materia Materia a crear
     * @return Completable
     */
    @POST("api/Materias")
    Completable addMateria(@Body @NotNull MateriaDTO materia);

    /**
     * Actualiza una materia en particular
     *
     * @param codigo  Codigo 'viejo' de la materia a actualizar
     * @param materia Materia a actualizar
     * @return Completable
     */
    @PUT("api/Materias/{codigo}")
    Completable updateMateria(@Path("codigo") long codigo, @Body @NotNull MateriaDTO materia);

    /**
     * Obtiene una lista con todos los periodos academicos creados
     *
     * @return List de PeriodoAcademicoDTO
     */
    @GET("api/PeriodoCarrera")
    Observable<List<PeriodoAcademicoDTO>> getAllPeriodosAcademicos();

    /**
     * Remueve varias periodos academicos
     *
     * @param idPeriodos Ids de los periodos a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/PeriodoCarrera")
    Completable removePeriodosAcademicos(@Query("idPeriodos") @NotNull String idPeriodos);

    /**
     * Remueve un periodo academico en particular
     *
     * @param idPeriodo Id del periodo a remover
     * @return Completable
     */
    @DELETE("api/PeriodoCarrera/{idPeriodo}")
    Completable removePeriodoAcademico(@Path("idPeriodo") long idPeriodo);

    /**
     * Agrega un periodo academico en particular
     *
     * @param periodo Periodo a crear
     * @return Completable
     */
    @POST("api/PeriodoCarrera")
    Completable addPeriodoAcademico(@Body @NotNull PeriodoAcademicoDTO periodo);

    /**
     * Actualiza un periodo academico en particular
     *
     * @param idPeriodo Id del periodo a actualizar
     * @param periodo   Periodo a actualizar
     * @return Completable
     */
    @PUT("api/PeriodoCarrera/{idPeriodo}")
    Completable updatePeriodoAcademico(@Path("idPeriodo") long idPeriodo, @Body @NotNull PeriodoAcademicoDTO periodo);

    /**
     * Remueve varios profesores
     *
     * @param cedulas Cedulas de los profesores a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Profesor")
    Completable removeProfesores(@Query("cedulas") @NotNull String cedulas);

    /**
     * Remueve un profesor en particular
     *
     * @param cedula Cedula del profesor a remover
     * @return Completable
     */
    @DELETE("api/Profesor/{cedula}")
    Completable removeProfesor(@Path("cedula") long cedula);

    /**
     * Agrega un profesor en particular
     *
     * @param profesor Profesor a crear
     * @return Completable
     */
    @POST("api/Profesor")
    Completable addProfesor(@Body @NotNull ProfesorDTO profesor);

    /**
     * Actualiza un profesor en particular
     *
     * @param cedula   Cedula 'vieja' del profesor a actualizar
     * @param profesor Profesor a actualizar
     * @return Completable
     */
    @PUT("api/Profesor/{cedula}")
    Completable updateProfesor(@Path("cedula") long cedula, @Body @NotNull ProfesorDTO profesor);

    /**
     * Obtiene una lista de relaciones profesor-materia
     *
     * @return List de ProfesorMateriaDetailsDTO
     */
    @GET("api/ProfesorMateria")
    Observable<List<ProfesorMateriaDetailsDTO>> getAllProfesorMateria();

    /**
     * Remueve varias relaciones entre profesor - materia
     *
     * @param ids Ids de las relaciones a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/ProfesorMateria")
    Completable removeProfesorMateria(@Query("ids") @NotNull String ids);

    /**
     * Remueve una relacion profesor-materia en particular
     *
     * @param id Id de la relacion a remover
     * @return Completable
     */
    @DELETE("api/ProfesorMateria/{id}")
    Completable removeProfesorMateria(@Path("id") long id);

    /**
     * Agrega una relacion profesor-materia en particular
     *
     * @param relacion Relacion a crear
     * @return Completable
     */
    @POST("api/ProfesorMateria")
    Completable addProfesorMateria(@Body @NotNull ProfesorMateriaDTO relacion);

    /**
     * Actualiza una materia en particular
     *
     * @param id       Id de la relacion a actualizar
     * @param relacion Relacion a actualizar
     * @return Completable
     */
    @PUT("api/ProfesorMateria/{id}")
    Completable updateProfesorMateria(@Path("id") long id, @Body @NotNull ProfesorMateriaDTO relacion);


    /**
     * Obtiene una lista de las secciones acorde al periodo academico activo
     *
     * @return List de SeccionDetailsDTO
     */
    @GET("api/Secciones")
    Observable<List<SeccionDetailsDTO>> getAllSecciones();

    /**
     * Remueve varias secciones
     *
     * @param cedulas Codigos de las secciones a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Secciones")
    Completable removeSecciones(@Query("codigos") @NotNull String cedulas);

    /**
     * Remueve una seccion en particular
     *
     * @param codigo Codigo de la seccion a remover
     * @return Completable
     */
    @DELETE("api/Secciones/{codigo}")
    Completable removeSeccion(@Path("codigo") long codigo);

    /**
     * Agrega una seccion en particular
     *
     * @param seccion Seccion a crear
     * @return Completable
     */
    @POST("api/Secciones")
    Completable addSeccion(@Body @NotNull SeccionDTO seccion);

    /**
     * Actualiza un profesor en particular
     *
     * @param codigo  Codigo 'viejo' de la seccion a actualizar
     * @param seccion Seccion a actualizar
     * @return Completable
     */
    @PUT("api/Secciones/{codigo}")
    Completable updateSeccion(@Path("codigo") long codigo, @Body @NotNull SeccionDTO seccion);

    /**
     * Obtiene una lista con todos los usuarios creados.
     *
     * @return List de UsuarioDetailsDTO
     */
    @GET("api/Account")
    Observable<List<UsuarioDetailsDTO>> getAllUsuarios();

    /**
     * Remueve varias usuarios
     *
     * @param cedulas Cedulas de los usuarios a remover (e.g: 1,2,3,4)
     * @return Completable
     */
    @DELETE("api/Account")
    Completable removeUsuarios(@Query("codigos") @NotNull String cedulas);

    /**
     * Remueve un usuario en particular
     *
     * @param cedula Cedula del usuario a remover
     * @return Completable
     */
    @DELETE("api/Account/{cedula}")
    Completable removeUsuario(@Path("cedula") long cedula);

    /**
     * Crea un usuario
     *
     * @param usuario Usuario a crear
     * @return Completable
     */
    @POST("api/Account")
    Completable addUsuario(@Body @NotNull UsuarioDTO usuario);

    /**
     * Actualiza un usuario en particular
     *
     * @param cedula  Cedula 'vieja' del usuario a actualizar
     * @param usuario Usuario a actualizar
     * @return Completable
     */
    @PUT("api/Account/{cedula}")
    Completable updateUsuario(@Path("cedula") long cedula, @Body @NotNull UsuarioDTO usuario);
}
