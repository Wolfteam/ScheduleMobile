package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class LoginPresenter<V extends LoginContractView>
        extends BasePresenter<V>
        implements LoginContractPresenter<V> {

    private ScheduleService mScheduleService;
    private PreferencesHelperContract mPreferenceHelper;

    @Inject
    public LoginPresenter(ScheduleService scheduleService, PreferencesHelperContract preferencesHelper) {
        mScheduleService = scheduleService;
        mPreferenceHelper = preferencesHelper;
    }

    @Override
    public void onLoginClick(String username, String password) {
        getView().showLoading();
        mScheduleService.getToken(username, password).enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    String username = response.body().getUsername();
                    TokenDTO token = response.body();
                    mPreferenceHelper.storeAccessToken(token.getAuthenticationToken());
                    getView().showSuccess("Autenticado el usuario: " + username + ". Su Token fue creado: " + token.getCreateDate());
                }else{
                    getView().showError("Usuario o clave invalidas");
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                getView().hideLoading();
                getView().showError("Ocurrio un error al comunicarse con la API");
            }
        });
    }
}