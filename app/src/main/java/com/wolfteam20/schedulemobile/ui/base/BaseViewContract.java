package com.wolfteam20.schedulemobile.ui.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface BaseViewContract extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideKeyboard();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(@StringRes int resId);

    /**
     * Si el token expira se debe abrir la activity
     * por default (Login en este caso)
     */
    void openActivityOnTokenExpire();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(@StringRes int resId);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSuccessMessage(@NotNull String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSuccessMessage(@StringRes int resId);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInfoMessage(@NotNull  String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInfoMessage(@StringRes int resId);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showWarningMessage(@NotNull String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showWarningMessage(@StringRes int resId);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorMessage(@NotNull String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorMessage(@StringRes int resId);
}
