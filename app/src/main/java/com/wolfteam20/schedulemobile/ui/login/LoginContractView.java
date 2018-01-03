package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface LoginContractView extends BaseContractView {
    void showLoading();

    void hideLoading();

    void showSuccess(String msg);

    void showError(String error);

    void intentToEventActivity();

}
