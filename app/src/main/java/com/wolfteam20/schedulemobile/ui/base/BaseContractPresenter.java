package com.wolfteam20.schedulemobile.ui.base;

/**
 * Created by Efrain.Bastidas on 1/3/2018.
 */

public interface BaseContractPresenter<V extends BaseContractView> {
    void onAttach(V view);

    void onDetach();

    void onDetachView();
}
