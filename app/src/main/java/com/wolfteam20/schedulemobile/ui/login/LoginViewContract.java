package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.ui.base.BaseViewContract;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface LoginViewContract extends BaseViewContract {
    void hideLoading();

    void intentToHomeActivity();

    void onBtnSignInClick();

    void setEnabledViews(boolean enabled);

    void showError(String error);

    void showLoading();
}
