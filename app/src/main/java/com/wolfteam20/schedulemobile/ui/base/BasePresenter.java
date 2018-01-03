package com.wolfteam20.schedulemobile.ui.base;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V mBaseView = null;

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
//        if (mBaseView == null)
//            return new NullPointerException("La vista no puede ser nula");
        return mBaseView;
    }
}
