package com.wolfteam20.schedulemobile.data;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.db.DbHelperContract;
import com.wolfteam20.schedulemobile.data.network.ApiScheduleContract;
import com.wolfteam20.schedulemobile.data.network.models.AulaDTO;
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.CarreraDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.PrioridadProfesorDTO;
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
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.di.qualifiers.ApiScheduleContext;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class DataManager implements DataManagerContract {
    private Context mContext;
    private PreferencesHelperContract mPreferencesHelper;
    private ApiScheduleContract mApiScheduleContract;
    private DbHelperContract mDbHelper;

    @Inject
    DataManager(@ApplicationContext Context context,
                PreferencesHelperContract prefs,
                @ApiScheduleContext ApiScheduleContract apiScheduleContract,
                DbHelperContract dbHelper) {
        mContext = context;
        mPreferencesHelper = prefs;
        mApiScheduleContract = apiScheduleContract;
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<List<AulaDetailsDTO>> getAllAulas() {
        return mApiScheduleContract.getAllAulas();
    }

    @Override
    public Observable<List<MateriaDetailsDTO>> getAllMaterias() {
        return mApiScheduleContract.getAllMaterias();
    }

    @Override
    public Completable removeMaterias(@NotNull String codigos) {
        return mApiScheduleContract.removeMaterias(codigos);
    }

    @Override
    public Completable removeMateria(long codigo) {
        return mApiScheduleContract.removeMateria(codigo);
    }

    @Override
    public Completable addMateria(@NotNull MateriaDTO materia) {
        return mApiScheduleContract.addMateria(materia);
    }

    @Override
    public Completable updateMateria(long codigo, @NotNull MateriaDTO materia) {
        return mApiScheduleContract.updateMateria(codigo, materia);
    }

    @Override
    public Observable<List<PeriodoAcademicoDTO>> getAllPeriodosAcademicos() {
        return mApiScheduleContract.getAllPeriodosAcademicos();
    }

    @Override
    public Completable removePeriodosAcademicos(@NotNull String idPeriodos) {
        return mApiScheduleContract.removePeriodosAcademicos(idPeriodos);
    }

    @Override
    public Completable removePeriodoAcademico(long idPeriodo) {
        return mApiScheduleContract.removePeriodoAcademico(idPeriodo);
    }

    @Override
    public Completable addPeriodoAcademico(@NotNull PeriodoAcademicoDTO periodo) {
        return mApiScheduleContract.addPeriodoAcademico(periodo);
    }

    @Override
    public Completable updatePeriodoAcademico(long idPeriodo, @NotNull PeriodoAcademicoDTO periodo) {
        return mApiScheduleContract.updatePeriodoAcademico(idPeriodo, periodo);
    }

    @Override
    public Single<List<PrioridadProfesorDTO>> getAllPrioridades() {
        return mApiScheduleContract.getAllPrioridades();
    }

    @Override
    public Completable removeProfesores(@NotNull String cedulas) {
        return mApiScheduleContract.removeProfesores(cedulas);
    }

    @Override
    public Completable removeProfesor(long cedula) {
        return mApiScheduleContract.removeProfesor(cedula);
    }

    @Override
    public Completable addProfesor(@NotNull ProfesorDTO profesor) {
        return mApiScheduleContract.addProfesor(profesor);
    }

    @Override
    public Completable updateProfesor(long cedula, @NotNull ProfesorDTO profesor) {
        return mApiScheduleContract.updateProfesor(cedula, profesor);
    }

    @Override
    public Observable<List<ProfesorDetailsDTO>> getAllProfesores() {
        return mApiScheduleContract.getAllProfesores();
    }

    @Override
    public Observable<List<ProfesorMateriaDetailsDTO>> getAllProfesorMateria() {
        return mApiScheduleContract.getAllProfesorMateria();
    }

    @Override
    public Completable removeProfesorMateria(@NotNull String ids) {
        return mApiScheduleContract.removeProfesorMateria(ids);
    }

    @Override
    public Completable removeProfesorMateria(long id) {
        return mApiScheduleContract.removeProfesorMateria(id);
    }

    @Override
    public Completable addProfesorMateria(@NotNull ProfesorMateriaDTO relacion) {
        return mApiScheduleContract.addProfesorMateria(relacion);
    }

    @Override
    public Completable updateProfesorMateria(long id, @NotNull ProfesorMateriaDTO relacion) {
        return mApiScheduleContract.updateProfesorMateria(id, relacion);
    }

    @Override
    public Observable<List<SeccionDetailsDTO>> getAllSecciones() {
        return mApiScheduleContract.getAllSecciones();
    }

    @Override
    public Completable removeSecciones(@NotNull String cedulas) {
        return mApiScheduleContract.removeSecciones(cedulas);
    }

    @Override
    public Completable removeSeccion(long codigo) {
        return mApiScheduleContract.removeSeccion(codigo);
    }

    @Override
    public Completable addSeccion(@NotNull SeccionDTO seccion) {
        return mApiScheduleContract.addSeccion(seccion);
    }

    @Override
    public Completable updateSeccion(long codigo, @NotNull SeccionDTO seccion) {
        return mApiScheduleContract.updateSeccion(codigo, seccion);
    }

    @Override
    public Observable<List<UsuarioDetailsDTO>> getAllUsuarios() {
        return mApiScheduleContract.getAllUsuarios();
    }

    @Override
    public Completable removeUsuarios(@NotNull String cedulas) {
        return mApiScheduleContract.removeUsuarios(cedulas);
    }

    @Override
    public Completable removeUsuario(long cedula) {
        return mApiScheduleContract.removeUsuario(cedula);
    }

    @Override
    public Completable addUsuario(@NotNull UsuarioDTO usuario) {
        return mApiScheduleContract.addUsuario(usuario);
    }

    @Override
    public Completable updateUsuario(long cedula, @NotNull UsuarioDTO usuario) {
        return mApiScheduleContract.updateUsuario(cedula, usuario);
    }

    @Override
    public Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico() {
        return mApiScheduleContract.getCurrentPeriodoAcademico();
    }

    @Override
    public Observable<DisponibilidadDetailsDTO> getDisponbilidad(int cedula) {
        return mApiScheduleContract.getDisponbilidad(cedula);
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionAcademica() {
        return mApiScheduleContract.getPlanificacionAcademica();
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionAulas() {
        return mApiScheduleContract.getPlanificacionAulas();
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionHorario() {
        return mApiScheduleContract.getPlanificacionHorario();
    }

    @Override
    public Observable<ProfesorDetailsDTO> getProfesor(int cedula) {
        return mApiScheduleContract.getProfesor(cedula);
    }

    @Override
    public Observable<Response<ResponseBody>> addDisponibilidad(List<DisponibilidadDTO> disponibilidades) {
        return mApiScheduleContract.addDisponibilidad(disponibilidades);
    }

    @Override
    public Single<List<CarreraDTO>> getAllCarreras() {
        return mApiScheduleContract.getAllCarreras();
    }

    @Override
    public Single<List<TipoAulaMateriaDTO>> getAllTipoAulaMateria() {
        return mApiScheduleContract.getAllTipoAulaMateria();
    }

    @Override
    public Single<List<PrivilegioDTO>> getAllPrivilegios() {
        return mApiScheduleContract.getAllPrivilegios();
    }

    @Override
    public Single<List<SemestreDTO>> getAllSemestres() {
        return mApiScheduleContract.getAllSemestres();
    }

    @Override
    public Completable removeAulas(@NotNull String idAulas) {
        return mApiScheduleContract.removeAulas(idAulas);
    }

    @Override
    public Completable removeAula(long idAula) {
        return mApiScheduleContract.removeAula(idAula);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public Completable addAula(@NotNull AulaDTO aula) {
        return mApiScheduleContract.addAula(aula);
    }

    @Override
    public Completable updateAula(long idAula, @NotNull AulaDTO aula) {
        return mApiScheduleContract.updateAula(idAula, aula);
    }

    @Override
    public Observable<Response<TokenDTO>> getToken(String username, String password, Boolean isMobile) {
        return mApiScheduleContract.getToken(username, password, isMobile);
    }

    @Override
    public boolean isUserAdmin() {
        return mPreferencesHelper.isUserAdmin();
    }

    @Override
    public int getCedula() {
        return mPreferencesHelper.getCedula();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public String getFullname() {
        return mPreferencesHelper.getFullname();
    }

    @Override
    public String getUsername() {
        return mPreferencesHelper.getUsername();
    }

    @Override
    public void storeAccessToken(String token) {
        mPreferencesHelper.storeAccessToken(token);
    }

    @Override
    public void storeUser(String token) {
        mPreferencesHelper.storeUser(token);
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula) {
        return mDbHelper.getDisponibilidadLocal(cedula);
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia) {
        return mDbHelper.getDisponibilidadLocal(cedula, idDia);
    }

    @Override
    public Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula) {
        return mDbHelper.getDisponibilidadDetailsLocal(cedula);
    }

    @Override
    public void addDisponibilidadLocal(@NotNull List<DisponibilidadDTO> disponibilidades) {
        mDbHelper.addDisponibilidadLocal(disponibilidades);
    }

    @Override
    public void addDisponibilidadDetailsLocal(@NotNull DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        mDbHelper.addDisponibilidadDetailsLocal(disponibilidadDetailsDTO);
    }

    @Override
    public void removeDisponibilidadLocal(int cedula) {
        mDbHelper.removeDisponibilidadLocal(cedula);
    }

    @Override
    public void removeDisponibilidadLocal(int cedula, int idDia) {
        mDbHelper.removeDisponibilidadLocal(cedula, idDia);
    }

    @Override
    public void removeDisponibilidadDetailsLocal(int cedula) {
        mDbHelper.removeDisponibilidadDetailsLocal(cedula);
    }
}
