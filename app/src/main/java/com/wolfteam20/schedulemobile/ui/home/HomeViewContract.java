package com.wolfteam20.schedulemobile.ui.home;

import android.view.View;

import com.wolfteam20.schedulemobile.ui.base.BaseViewContract;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface HomeViewContract extends BaseViewContract {
    void onBtnPlanificacionClick(View view);

    void hideLoading();

    void requestWritePermission();

    /**
     * Muestra un alertdialog explicando porque se nitan los permisos de escritura
     */
    void showRequestWritePermissionExplanation();

    void showCurrentPeriodo(String periodo);

    void showDownloadProgressIndicator();

    void showLoading();

    void stopDownloadProgressIndicator();
}
