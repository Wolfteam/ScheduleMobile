package com.wolfteam20.schedulemobile.ui.base;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface BaseContractPresenter<V extends BaseContractView> {
    /**
     * Guarda la vista pasada para poder luego manipularla desde el Presenter
     * @param view View a guardar
     */
    void onAttach(V view);

    void onDetach();

    void onDetachView();
}
