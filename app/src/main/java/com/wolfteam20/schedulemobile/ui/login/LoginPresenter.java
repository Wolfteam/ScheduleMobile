package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.ui.base.BaseContract;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class LoginPresenter<V extends LoginContract.View>
        extends BasePresenter<V>
        implements LoginContract.Presenter<V> {

    @Override
    public void onLoginClick(String username, String pass) {
        if (username.contains("gmail")){
            getView().showLoading();
            getView().hideLoading();
        }else{
            getView().showError("El usuario no contiene gmail");
        }

    }
}