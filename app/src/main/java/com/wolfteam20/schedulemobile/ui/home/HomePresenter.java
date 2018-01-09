package com.wolfteam20.schedulemobile.ui.home;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.data.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
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
        getView().showLoading();
        switch (tipoPlanificacion) {
            case 1:
                mDataManager.getPlanificacionAcademica("Bearer " + mDataManager.getToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody>  response) {
                        getView().hideLoading();
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    String filename = "academica.xlsx";
                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), filename);
                                    try {
                                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                                        sink.writeAll(response.body().source());
                                        sink.close();
                                    } catch (IOException e) {
                                        Log.e("Error", e.getMessage());
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        getView().hideLoading();
                        getView().showError("Ocurrio un error al comunicarse con la api." + t.getMessage());
                    }
                });
                break;
            case 2:
                mDataManager.getPlanificacionAulas("Bearer " + mDataManager.getToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody>  response) {
                        getView().hideLoading();
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    String filename = "aulas.xlsx";
                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), filename);
                                    try {
                                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                                        sink.writeAll(response.body().source());
                                        sink.close();
                                    } catch (IOException e) {
                                        Log.e("Error", e.getMessage());
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        getView().hideLoading();
                        getView().showError("Ocurrio un error al comunicarse con la api." + t.getMessage());
                    }
                });
                break;
            case 3:
                mDataManager.getPlanificacionHorario("Bearer " + mDataManager.getToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody>  response) {
                        getView().hideLoading();
                        if (response.isSuccessful()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    String filename = "horarios.xlsx";
                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), filename);
                                    try {
                                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                                        sink.writeAll(response.body().source());
                                        sink.close();
                                    } catch (IOException e) {
                                        Log.e("Error", e.getMessage());
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        getView().hideLoading();
                        getView().showError("Ocurrio un error al comunicarse con la api." + t.getMessage());
                    }
                });
                break;
        }
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
