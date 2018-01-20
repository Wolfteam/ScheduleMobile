package com.wolfteam20.schedulemobile.ui.home;

import android.os.Environment;

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

public class HomePresenter<V extends HomeViewContract>
        extends BasePresenter<V>
        implements HomePresenterContract<V> {
    //TODO: REEMPLZAR TODOS LOS throwable CON UN ERROR GENERICO EN TODOS LOS PRESENTER
    @Inject
    HomePresenter(CompositeDisposable compositeDisposable, DataManagerContract dataManager) {
        super(compositeDisposable, dataManager);
    }

    @Override
    public void getPlanificacion(int tipoPlanificacion) {
        if (!getView().isNetworkAvailable()) {
            getView().showError("No hay conexion.");
            return;
        }
        getView().showDownloadProgressIndicator();
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
                                        success -> getView().showError(success),
                                        throwable -> {
                                            getView().hideLoading();
                                            getView().showError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getView().stopDownloadProgressIndicator()
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
                                        success -> getView().showError(success),
                                        throwable -> {
                                            getView().hideLoading();
                                            getView().showError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getView().stopDownloadProgressIndicator()
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
                                        success -> getView().showError(success),
                                        throwable -> {
                                            getView().hideLoading();
                                            getView().showError(throwable.getMessage());
                                            Timber.e(throwable);
                                        },
                                        () -> getView().stopDownloadProgressIndicator()
                                )
                );
                break;
        }
    }

    @Override
    public void getCurrentPeriodo() {
        getView().showLoading();
        getCompositeDisposable().add(getDataManager().getCurrentPeriodoAcademico()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response ->
                        {
                            if (!response.isSuccessful()) {
                                getView().showError("El token expiro?");
                                return;
                            }
                            getView().showCurrentPeriodo(response.body().getNombrePeriodo());
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError(throwable.getMessage());
                            Timber.e(throwable);
                        },
                        () -> getView().hideLoading()
                )
        );
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
