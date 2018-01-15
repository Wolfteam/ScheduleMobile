package com.wolfteam20.schedulemobile.ui.disponibilidad;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface DispContractPresenter<V extends DispContractView> extends BaseContractPresenter<V> {

    void onDiaClicked(int dia);

    /**
     * Evento que ocurre al seleccionar un profesor del dropdown.
     * Obtiene la disponibilidad del prof.
     *
     * @param cedula Cedula del prof. indicado
     */
    void onProfesorSelected(int cedula);

    void subscribe();
}
