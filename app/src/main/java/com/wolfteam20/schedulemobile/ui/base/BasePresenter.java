package com.wolfteam20.schedulemobile.ui.base;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BasePresenter<V extends BaseContractView> implements BaseContractPresenter<V> {

    private V mBaseView;

    @Override
    public void onAttach(V view) {
        mBaseView = view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onDetachView() {

    }

    public V getView(){
        if (mBaseView == null)
            throw new NullPointerException("La vista no puede ser nula");
        return mBaseView;
    }
}
