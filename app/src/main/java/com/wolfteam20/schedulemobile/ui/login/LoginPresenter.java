package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        mDataManager.getToken(username, password, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                        {
                            if (response.isSuccessful()) {
                                TokenDTO token = response.body();
                                mDataManager.storeAccessToken(token.getAuthenticationToken());
                                mDataManager.storeUserRole();
                                getView().intentToHomeActivity();
                            } else {
                                getView().showError("Usuario o clave invalidas");
                            }
                        }, throwable -> getView().showError(throwable.getMessage()),
                        () -> getView().hideLoading()
                );
    }

    @Override
    public void subscribe() {
        if (!mDataManager.getToken().isEmpty())
            getView().intentToHomeActivity();
    }
}