package com.wolfteam20.schedulemobile.ui.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface BaseViewContract extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void hideKeyboard();

    @StateStrategyType(SkipStrategy.class)
    void onError(String message);

    @StateStrategyType(SkipStrategy.class)
    void onError(@StringRes int resId);

    /**
     * Si el token expira se debe abrir la activity
     * por default (Login en este caso)
     */
    void openActivityOnTokenExpire();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void showSuccessMessage(@NotNull String message);

    @StateStrategyType(SkipStrategy.class)
    void showSuccessMessage(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void showInfoMessage(@NotNull  String message);

    @StateStrategyType(SkipStrategy.class)
    void showInfoMessage(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void showWarningMessage(@NotNull String message);

    @StateStrategyType(SkipStrategy.class)
    void showWarningMessage(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(@NotNull String message);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(@StringRes int resId);
}
