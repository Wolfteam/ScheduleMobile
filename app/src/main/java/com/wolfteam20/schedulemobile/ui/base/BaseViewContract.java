package com.wolfteam20.schedulemobile.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface BaseViewContract {

    void hideKeyboard();

    /**
     * Indica si la red esta disponible para su uso
     * @return True en caso de estarlo
     */
    boolean isNetworkAvailable();


    void onError(String message);

    void onError(@StringRes int resId);

    /**
     * Si el token expira se debe abrir la activity
     * por default (Login en este caso)
     */
    void openActivityOnTokenExpire();

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}
