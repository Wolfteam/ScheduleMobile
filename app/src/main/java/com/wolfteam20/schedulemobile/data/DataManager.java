package com.wolfteam20.schedulemobile.data;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.db.DbHelperContract;
import com.wolfteam20.schedulemobile.data.network.ApiSchedule;
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

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class DataManager implements DataManagerContract {
    private Context mContext;
    private PreferencesHelperContract mPreferencesHelper;
    private ApiSchedule mApiSchedule;
    private DbHelperContract mDbHelper;

    @Inject
    DataManager(@ApplicationContext Context context,
                PreferencesHelperContract prefs,
                ApiSchedule apiSchedule,
                DbHelperContract dbHelper) {
        mContext = context;
        mPreferencesHelper = prefs;
        mApiSchedule = apiSchedule;
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<List<AulaDetailsDTO>> getAllAulas() {
        return mApiSchedule.getAllAulas();
    }

    @Override
    public Observable<List<MateriaDetailsDTO>> getAllMaterias() {
        return mApiSchedule.getAllMaterias();
    }

    @Override
    public Observable<List<PeriodoAcademicoDTO>> getAllPeriodosAcademicos() {
        return mApiSchedule.getAllPeriodosAcademicos();
    }

    @Override
    public Observable<List<ProfesorDetailsDTO>> getAllProfesores() {
        return mApiSchedule.getAllProfesores();
    }

    @Override
    public Observable<List<ProfesorMateriaDetailsDTO>> getAllProfesorMateria() {
        return mApiSchedule.getAllProfesorMateria();
    }

    @Override
    public Observable<List<SeccionesDetailsDTO>> getAllSecciones() {
        return mApiSchedule.getAllSecciones();
    }

    @Override
    public Observable<List<UsuarioDetailsDTO>> getAllUsuarios() {
        return mApiSchedule.getAllUsuarios();
    }

    @Override
    public Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico() {
        return mApiSchedule.getCurrentPeriodoAcademico();
    }

    @Override
    public Observable<DisponibilidadDetailsDTO> getDisponbilidad(int cedula) {
        return mApiSchedule.getDisponbilidad(cedula);
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionAcademica() {
        return mApiSchedule.getPlanificacionAcademica();
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionAulas() {
        return mApiSchedule.getPlanificacionAulas();
    }

    @Override
    public Observable<Response<ResponseBody>> getPlanificacionHorario() {
        return mApiSchedule.getPlanificacionHorario();
    }

    @Override
    public Observable<ProfesorDetailsDTO> getProfesor(int cedula) {
        return mApiSchedule.getProfesor(cedula);
    }

    @Override
    public Observable<Response<ResponseBody>> postDisponibilidad(List<DisponibilidadDTO> disponibilidades) {
        return mApiSchedule.postDisponibilidad(disponibilidades);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public Observable<Response<TokenDTO>> getToken(String username, String password, Boolean isMobile) {
        return mApiSchedule.getToken(username, password, isMobile);
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
    public void saveAulasLocal(List<AulaDetailsDTO> aulas) {
        mDbHelper.saveAulasLocal(aulas);
    }

    @Override
    public void saveDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades) {
        mDbHelper.saveDisponibilidadLocal(disponibilidades);
    }

    @Override
    public void saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        mDbHelper.saveDisponibilidadDetailsLocal(disponibilidadDetailsDTO);
    }

    @Override
    public void removeAulaLocal(int idAula) {
        mDbHelper.removeAulaLocal(idAula);
    }

    @Override
    public void removeAulasLocal() {
        mDbHelper.removeAulasLocal();
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
