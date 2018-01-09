package com.wolfteam20.schedulemobile.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public class HomeFragment extends BaseFragment implements HomeContractView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    HomeContractPresenter<HomeContractView> mPresenter;

    @BindView(R.id.home_fragment_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_fragment_periodo_actual) TextView mPeriodoActual;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        mPeriodoActual.setText("");
        mPresenter.getCurrentPeriodo();
    }

    @OnClick({R.id.btnPlanificacionAcademica, R.id.btnPlanificacionAulas, R.id.btnPlanificacionHorario})
    @Override
    public void onBtnPlanificacionClick(View view){
        switch (view.getId()){
            case R.id.btnPlanificacionAcademica:
                Toast.makeText(getContext(),"Academica", Toast.LENGTH_SHORT).show();
                mPresenter.getPlanificacion(1);
                break;
            case R.id.btnPlanificacionAulas:
                Toast.makeText(getContext(),"Aulas", Toast.LENGTH_SHORT).show();
                mPresenter.getPlanificacion(2);
                break;
            case R.id.btnPlanificacionHorario:
                Toast.makeText(getContext(),"Horario", Toast.LENGTH_SHORT).show();
                mPresenter.getPlanificacion(3);
                break;
        }
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        mPresenter.onAttach(this);
        mPresenter.subscribe();
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCurrentPeriodo(String periodo) {
        mPeriodoActual.setText(periodo);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
    }
}
