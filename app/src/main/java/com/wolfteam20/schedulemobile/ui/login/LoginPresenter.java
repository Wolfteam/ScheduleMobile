package com.wolfteam20.schedulemobile.ui.login;

import com.arellomobile.mvp.InjectViewState;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.network.models.TokenDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginViewContract> implements LoginPresenterContract {

    @Inject
    LoginPresenter(CompositeDisposable compositeDisposable, DataManagerContract dataManager) {
        super(compositeDisposable, dataManager);
        subscribe();
    }

    @Override
    public void onBtnSignInClick(String username, String password) {
        if (!isNetworkAvailable()) {
            getViewState().onError(R.string.no_network);
            return;
        }
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        String dateFormated = dateFormat.format(currentDate);

        getViewState().showLoading();
        getCompositeDisposable().add(getDataManager().getToken(username, password, true, dateFormated)
                .subscribe(response ->
                        {
                            if (response.isSuccessful()) {
                                TokenDTO token = response.body();
                                getDataManager().storeAccessToken(token.getAuthenticationToken());
                                getDataManager().storeUser(token.getAuthenticationToken());
                                getViewState().intentToHomeActivity();
                            } else {
                                getViewState().onError(R.string.wrong_username_password);
                            }
                        }, throwable -> {
                            getViewState().hideLoading();
                            getViewState().onError(throwable.getLocalizedMessage());
                            Timber.e(throwable);
                        },
                        () -> getViewState().hideLoading()
                )
        );
    }

    @Override
    public void subscribe() {
        if (!getDataManager().getToken().isEmpty())
            getViewState().intentToHomeActivity();
    }
}