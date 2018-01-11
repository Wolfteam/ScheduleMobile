package com.wolfteam20.schedulemobile.ui.base;

import com.wolfteam20.schedulemobile.data.DataManagerContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BasePresenter<V extends BaseContractView> implements BaseContractPresenter<V> {

    private final CompositeDisposable mCompositeDisposable;
    private final DataManagerContract mDataManager;
    private V mBaseView;

    public BasePresenter(CompositeDisposable mCompositeDisposable, DataManagerContract mDataManager) {
        this.mCompositeDisposable = mCompositeDisposable;
        this.mDataManager = mDataManager;
    }

    public DataManagerContract getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public V getView(){
        if (mBaseView == null)
            throw new NullPointerException("La vista no puede ser nula");
        return mBaseView;
    }

    @Override
    public void onAttach(V view) {
        mBaseView = view;
    }

    @Override
    public void onDetach() {
        mBaseView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDetachView() {

    }
}
