package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */


@ActivityScope
public interface LoginPresenterContract extends BasePresenterContract {

    void onBtnSignInClick(String username, String pass);

    void subscribe();

    //Uri getFirstStepUri();

    //void onHandleAuthIntent(intent: Intent?);
}
