package com.wolfteam20.schedulemobile.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.arellomobile.mvp.InjectViewState;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.data.DataManagerContract;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;
import com.wolfteam20.schedulemobile.utils.Constants;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import timber.log.Timber;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@InjectViewState
public class HomePresenter extends BasePresenter<HomeViewContract> implements HomePresenterContract {

    @Inject
    HomePresenter(CompositeDisposable compositeDisposable, DataManagerContract dataManager) {
        super(compositeDisposable, dataManager);
        subscribe();
    }

    @Override
    public void getPlanificacion(int tipoPlanificacion) {
        if (!isNetworkAvailable()) {
            getViewState().onError(R.string.no_network);
            return;
        }
        getViewState().showDownloadProgressIndicator();
        switch (tipoPlanificacion) {
            case 1://Planificacion Academica
                getCompositeDisposable().add(
                        getDataManager().getPlanificacionAcademica()
                                .flatMap(response ->
                                        Observable.create((ObservableOnSubscribe<String>) emitter ->
                                        {
                                            if (!response.isSuccessful()) {
                                                emitter.onComplete();
                                                return;
                                            }
                                            saveFile(Constants.PLANIF_ACADE_FILENAME, response.body(), emitter);
                                        })
                                )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        success -> getViewState().onError(success),
                                        throwable -> {
                                            getViewState().hideLoading();
                                            getViewState().onError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getViewState().stopDownloadProgressIndicator()
                                )
                );
                break;
            case 2://Planificacion aulas
                getCompositeDisposable().add(
                        getDataManager().getPlanificacionAulas()
                                .flatMap(response ->
                                        Observable.create((ObservableOnSubscribe<String>) emitter ->
                                        {
                                            if (!response.isSuccessful()) {
                                                emitter.onComplete();
                                                return;
                                            }
                                            saveFile(Constants.PLANIF_AULAS_FILENAME, response.body(), emitter);
                                        })
                                )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        success -> getViewState().onError(success),
                                        throwable -> {
                                            getViewState().hideLoading();
                                            getViewState().onError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getViewState().stopDownloadProgressIndicator()
                                )
                );
                break;
            case 3://Planificacion horarios
                getCompositeDisposable().add(
                        getDataManager().getPlanificacionHorario()
                                .flatMap(response ->
                                        Observable.create((ObservableOnSubscribe<String>) emitter ->
                                        {
                                            if (!response.isSuccessful()) {
                                                emitter.onComplete();
                                                return;
                                            }
                                            saveFile(Constants.PLANIF_HORAR_FILENAME, response.body(), emitter);
                                        })
                                )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        success -> getViewState().onError(success),
                                        throwable -> {
                                            getViewState().hideLoading();
                                            getViewState().onError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getViewState().stopDownloadProgressIndicator()
                                )
                );
                break;
        }
    }

    @Override
    public void getCurrentPeriodo() {
        if (!isNetworkAvailable()) {
            getViewState().onError(R.string.no_network);
            return;
        }
        getViewState().showLoading();
        getCompositeDisposable().add(getDataManager().getCurrentPeriodoAcademico()
                .subscribe(
                        response ->
                        {
                            if (!response.isSuccessful()) {
                                getDataManager().storeAccessToken("");
                                getViewState().openActivityOnTokenExpire();
                                return;
                            }
                            getViewState().showCurrentPeriodo(response.body().getNombrePeriodo());
                        },
                        throwable -> {
                            getViewState().hideLoading();
                            getViewState().onError(throwable.getMessage());
                            Timber.e(throwable);
                        },
                        () -> getViewState().hideLoading()
                )
        );
    }

    @Override
    public boolean isWritePermissionGranted() {
        if (ContextCompat.checkSelfPermission(getDataManager().getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    getDataManager().getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                getViewState().showRequestWritePermissionExplanation();
            } else {
                // No explanation needed, we can request the permission.
                // Esto varia si es una Activity (ActivityCompat.requestPermissions()) o un Fragment
                getViewState().requestWritePermission();
            }
            return false;
        }
        return true;
    }

    @Override
    public void saveFile(String filename, ResponseBody responseBody, ObservableEmitter<String> emitter) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsoluteFile(), filename);
        try {
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(responseBody.source());
            sink.close();
            emitter.onNext("Archivo " + filename + " descargado");
            emitter.onComplete();
        } catch (IOException e) {
            Timber.e(e);
            emitter.onError(e);
        }
    }

    @Override
    public void subscribe() {
        getCurrentPeriodo();
    }
}
