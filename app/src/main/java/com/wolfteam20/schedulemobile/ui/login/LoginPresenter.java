package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;
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

    private final DataManagerContract mDataManager;

    @Inject
    LoginPresenter(DataManagerContract dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onBtnSignInClick(String username, String password) {
        getView().showLoading();
        mDataManager.getToken(username, password).enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    TokenDTO token = response.body();
                    mDataManager.storeAccessToken(token.getAuthenticationToken());
                    mDataManager.storeUserRole();
                    getView().intentToHomeActivity();
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

    @Override
    public void subscribe() {
        if (!mDataManager.getToken().isEmpty())
            getView().intentToHomeActivity();
    }
}