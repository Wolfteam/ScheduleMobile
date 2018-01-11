package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class LoginPresenter<V extends LoginContractView>
        extends BasePresenter<V>
        implements LoginContractPresenter<V> {

    @Inject
    LoginPresenter(CompositeDisposable compositeDisposable, DataManagerContract dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void onBtnSignInClick(String username, String password) {
        getView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getToken(username, password, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                        {
                            if (response.isSuccessful()) {
                                TokenDTO token = response.body();
                                getDataManager().storeAccessToken(token.getAuthenticationToken());
                                getDataManager().storeUserRole();
                                getView().intentToHomeActivity();
                            } else {
                                getView().showError("Usuario o clave invalidas");
                            }
                        }, throwable -> getView().showError(throwable.getMessage()),
                        () -> getView().hideLoading()
                )
        );
    }

    @Override
    public void subscribe() {
        if (!getDataManager().getToken().isEmpty())
            getView().intentToHomeActivity();
    }
}