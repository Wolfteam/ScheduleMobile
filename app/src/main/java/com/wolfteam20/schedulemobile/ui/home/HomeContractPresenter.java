package com.wolfteam20.schedulemobile.ui.home;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface HomeContractPresenter<V extends HomeContractView> extends BaseContractPresenter<V> {
    void getPlanificacion(int tipoPlanificacion);
    void getCurrentPeriodo();
    void subscribe();
}
