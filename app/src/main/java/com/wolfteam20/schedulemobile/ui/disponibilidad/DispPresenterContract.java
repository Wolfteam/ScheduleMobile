package com.wolfteam20.schedulemobile.ui.disponibilidad;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface DispPresenterContract extends BasePresenterContract{

    /**
     * Inicia la activity Details pasandole el dia incado
     * @param dia Dia en donde se hizo click
     */
    void onDiaClicked(int dia);

    /**
     * Obtiene la disponibilidad del prof. seleccionado en el dropdown y actualiza la vista
     * @param cedula Cedula del prof. indicado
     * @param isActivityRecreated Indica si la activity/fragment fue recreado (e.g cambio de config.)
     */
    void onProfesorSelected(int cedula,int position, boolean isActivityRecreated);

    /**
     * Actualiza las horas asignadas/restantes de forma visual en base a la data
     * guardada en DispDetails activity
     * @param cedula Cedula de la cual se obtendra la data
     */
    void onHorasUpdatedLocal(int cedula);

    /**
     * Guarda la disponibilidad en la API
     * @param cedula Cedula de la disponibilidad que se guardara
     */
    void saveDisponibilidad(int cedula);

    void subscribe();
}
