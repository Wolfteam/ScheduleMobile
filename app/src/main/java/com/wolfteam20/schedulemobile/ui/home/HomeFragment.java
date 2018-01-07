package com.wolfteam20.schedulemobile.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public class HomeFragment extends BaseFragment implements HomeContractView {

    @Inject
    HomeContractPresenter<HomeContractView> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        mPresenter.onAttach(this);
        mPresenter.subscribe();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showCurrentPeriodo(String periodo) {

    }

    @Override
    public void showLoading() {

    }
}
