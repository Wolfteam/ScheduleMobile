package com.wolfteam20.schedulemobile.ui.base;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.utils.NetworkUtilities;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class BasePresenter<V extends BaseViewContract> extends MvpPresenter<V> implements BasePresenterContract {

    private final CompositeDisposable mCompositeDisposable;
    private final DataManagerContract mDataManager;

    public BasePresenter(@NonNull CompositeDisposable mCompositeDisposable, @NonNull DataManagerContract mDataManager) {
        this.mCompositeDisposable = mCompositeDisposable;
        this.mDataManager = mDataManager;
    }

    public DataManagerContract getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtilities.isNetworkAvailable(mDataManager.getContext());
    }
}
