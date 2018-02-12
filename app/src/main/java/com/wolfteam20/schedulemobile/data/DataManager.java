package com.wolfteam20.schedulemobile.data;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.db.DbHelperContract;
import com.wolfteam20.schedulemobile.data.network.ApiScheduleContract;
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
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

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
                ApiScheduleContract apiScheduleContract,
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
    public Observable<List<PeriodoAcademicoDTO>> getAllPeriodosAcademicos() {
        return mApiScheduleContract.getAllPeriodosAcademicos();
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
    public Observable<List<SeccionesDetailsDTO>> getAllSecciones() {
        return mApiScheduleContract.getAllSecciones();
    }

    @Override
    public Observable<List<UsuarioDetailsDTO>> getAllUsuarios() {
        return mApiScheduleContract.getAllUsuarios();
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
    public Completable removeAulas(String idAulas) {
        return mApiScheduleContract.removeAulas(idAulas);
    }

    @Override
    public Completable removeAula(long idAula) {
        return mApiScheduleContract.removeAula(idAula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public Completable addAula(AulaDTO aula) {
        return mApiScheduleContract.addAula(aula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
    }

    @Override
    public Completable updateAula(long idAula, AulaDTO aula) {
        return mApiScheduleContract.updateAula(idAula, aula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
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
    public Observable<List<AulaDetailsDTO>> getAllAulasLocal() {
        return mDbHelper.getAllAulasLocal();
    }

    @Override
    public Single<AulaDetailsDTO> getAulaLocal(long idAula) {
        return mDbHelper.getAulaLocal(idAula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
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
    public void addAulasLocal(List<AulaDetailsDTO> aulas) {
        mDbHelper.addAulasLocal(aulas);
    }

    @Override
    public void addDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades) {
        mDbHelper.addDisponibilidadLocal(disponibilidades);
    }

    @Override
    public void addDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        mDbHelper.addDisponibilidadDetailsLocal(disponibilidadDetailsDTO);
    }

    @Override
    public void removeAulaLocal(long idAula) {
        mDbHelper.removeAulaLocal(idAula);
    }

    @Override
    public void removeAulasLocal() {
        mDbHelper.removeAulasLocal();
    }

    @Override
    public void addAulaLocal(AulaDetailsDTO aula) {
        mDbHelper.addAulaLocal(aula);
    }

    @Override
    public void updateAulaLocal(AulaDetailsDTO aula) {
        mDbHelper.updateAulaLocal(aula);
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
