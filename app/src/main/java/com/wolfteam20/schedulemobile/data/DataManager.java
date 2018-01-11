package com.wolfteam20.schedulemobile.data;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.network.ApiSchedule;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;

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

    @Inject
    DataManager(@ApplicationContext Context context, PreferencesHelperContract prefs, ApiSchedule apiSchedule) {
        mContext = context;
        mPreferencesHelper = prefs;
        mApiSchedule = apiSchedule;
    }

    @Override
    public Observable<Response<PeriodoAcademicoDTO>> getCurrentPeriodoAcademico() {
        return mApiSchedule.getCurrentPeriodoAcademico();
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
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public Observable<Response<TokenDTO>> getToken(String username, String password, Boolean isMobile) {
        return mApiSchedule.getToken(username, password, isMobile);
    }

    @Override
    public String getUsername() {
        return mPreferencesHelper.getUsername();
    }

    @Override
    public String getFullname() {
        return mPreferencesHelper.getFullname();
    }

    @Override
    public boolean isUserAdmin() {
        return mPreferencesHelper.isUserAdmin();
    }

    @Override
    public void storeAccessToken(String token) {
        mPreferencesHelper.storeAccessToken(token);
    }

    @Override
    public void storeUserRole() {
        mPreferencesHelper.storeUserRole();
    }
}
