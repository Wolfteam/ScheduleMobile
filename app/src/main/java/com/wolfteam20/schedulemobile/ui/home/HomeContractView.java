package com.wolfteam20.schedulemobile.ui.home;

import android.view.View;

import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface HomeContractView extends BaseContractView {
    void onBtnPlanificacionClick(View view);

    /**
     * Verifica si tiene permisos de escritura, si no los tiene los pide.
     * Esto se usa para poder guardar el archivo de planificacion
     * @return True en caso de tenerlos
     */
    boolean isWritePermissionGranted();

    void hideLoading();

    void showCurrentPeriodo(String periodo);

    void showDownloadProgressIndicator();

    void showError(String error);

    void showLoading();

    void stopDownloadProgressIndicator();
}
