package com.wolfteam20.schedulemobile.ui.base;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public interface BaseContract {

    interface View {
        boolean isNetworkAvailable();
    }

    interface Presenter<V extends View> {

        void onAttach(V view);

        void onDetach();

        void onDetachView();
    }
}
