package com.wolfteam20.schedulemobile.ui.home;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

public class HomePresenter<V extends HomeContractView>
        extends BasePresenter<V>
        implements HomeContractPresenter<V> {

    private final DataManagerContract mDataManager;

    @Inject
    HomePresenter(DataManagerContract dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getPlanificacion(int tipoPlanificacion) {

    }

    @Override
    public void getCurrentPeriodo() {
        getView().showLoading();
        mDataManager.getCurrentPeriodoAcademico().enqueue(new Callback<PeriodoAcademicoDTO>() {
            @Override
            public void onResponse(Call<PeriodoAcademicoDTO> call, Response<PeriodoAcademicoDTO> response) {
                if (response.isSuccessful()) {
                    getView().hideLoading();
                    getView().showCurrentPeriodo(response.body().getNombrePeriodo());
                }
            }

            @Override
            public void onFailure(Call<PeriodoAcademicoDTO> call, Throwable t) {
                getView().hideLoading();
                getView().showError("Ocurrio un error al comunicarse con la api. " + t.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {
        getCurrentPeriodo();
    }


}
