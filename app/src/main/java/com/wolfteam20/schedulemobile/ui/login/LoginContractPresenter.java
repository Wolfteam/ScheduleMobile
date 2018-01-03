package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */


//@ActivityScope
public interface LoginContractPresenter<V extends LoginContractView> extends BaseContractPresenter<V> {

    //void subscribe()

    void onLoginClick(String username, String pass);

    //Uri getFirstStepUri();

    //void onHandleAuthIntent(intent: Intent?);
}
