package com.wolfteam20.schedulemobile.data;

import android.content.Context;

import com.wolfteam20.schedulemobile.data.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class DataManager implements DataManagerContract {
    private Context mContext;
    private PreferencesHelperContract mPreferencesHelper;
    private ScheduleService mScheduleService;

    @Inject
    DataManager(@ApplicationContext Context context, PreferencesHelperContract prefs, ScheduleService scheduleService) {
        mContext = context;
        mPreferencesHelper = prefs;
        mScheduleService = scheduleService;
    }

    @Override
    public Call<PeriodoAcademicoDTO> getCurrentPeriodoAcademico() {
        return  mScheduleService.getCurrentPeriodoAcademico();
    }

    @Override
    public Call<ResponseBody> getPlanificacionAcademica() {
        return null;
    }

    @Override
    public Call<ResponseBody> getPlanificacionAulas() {
        return null;
    }

    @Override
    public Call<ResponseBody> getPlanificacionHorario() {
        return null;
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }
    @Override

    public Call<TokenDTO> getToken(String username, String password) {
        return mScheduleService.getToken(username, password);
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
