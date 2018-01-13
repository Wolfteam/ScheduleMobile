package com.wolfteam20.schedulemobile.ui.disponibilidad;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface DisponibilidadContractPresenter<V extends DisponibilidadContractView> extends BaseContractPresenter<V> {
    void subscribe();
}
