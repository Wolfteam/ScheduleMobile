package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface LoginContractView extends BaseContractView {
    void hideLoading();

    void intentToHomeActivity();

    void onBtnSignInClick();

    void setEnabledViews(boolean enabled);

    void showError(String error);

    void showLoading();

    void showSuccess(String msg);
}
