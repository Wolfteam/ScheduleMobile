package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class LoginPresenter<V extends LoginViewContract>
        extends BasePresenter<V>
        implements LoginPresenterContract<V> {

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
                                        getDataManager().storeUser(token.getAuthenticationToken());
                                        getView().intentToHomeActivity();
                                    } else {
                                        getView().showError("Usuario o clave invalidas");
                                    }
                                }, throwable -> {
                                    getView().hideLoading();
                                    getView().showError(throwable.getMessage());
                                    Timber.e(throwable);
                                },
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