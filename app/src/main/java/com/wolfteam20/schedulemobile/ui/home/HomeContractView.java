package com.wolfteam20.schedulemobile.ui.home;

import android.view.View;

import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface HomeContractView extends BaseContractView{
    void onBtnPlanificacionClick(View view);
    void hideLoading();
    void showCurrentPeriodo(String periodo);
    void showError(String error);
    void showLoading();
}
