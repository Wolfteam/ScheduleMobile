package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContract;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public interface LoginContract {

    interface View extends BaseContract.View {

        void showLoading();

        void hideLoading();

        void showSuccess();

        void showError(String error);

        void intentToEventActivity();

    }

    @ActivityScope
    interface Presenter<V extends LoginContract.View> extends BaseContract.Presenter<V> {

        //void subscribe()

        void onLoginClick(String username, String pass);

        //Uri getFirstStepUri();

        //void onHandleAuthIntent(intent: Intent?);
    }
}
