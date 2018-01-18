package com.wolfteam20.schedulemobile.ui.disponibilidad;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface DispContractPresenter<V extends DispContractView> extends BaseContractPresenter<V> {

    /**
     * Inicia la activity Details pasandole el dia incado
     * @param dia Dia en donde se hizo click
     */
    void onDiaClicked(int dia);

    /**
     * Obtiene la disponibilidad del prof. seleccionado en el dropdown y actualiza la vista
     * @param cedula Cedula del prof. indicado
     */
    void onProfesorSelected(int cedula);

    void onHorasUpdatedLocal(int cedula);

    void saveDisponibilidad(int cedula);

    void subscribe();
}
