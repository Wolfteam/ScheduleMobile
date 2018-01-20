package com.wolfteam20.schedulemobile.ui.base;

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

    /**
     * Si el token expira se debe abrir la activity
     * por default (Login en este caso)
     */
    void openActivityOnTokenExpire();
}
