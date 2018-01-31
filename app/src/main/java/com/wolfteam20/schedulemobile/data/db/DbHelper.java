package com.wolfteam20.schedulemobile.data.db;

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO_;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO_;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

public class DbHelper implements DbHelperContract {
    private Box<DisponibilidadDTO> mDispBox;
    private Box<DisponibilidadDetailsDTO> mDispDetailsBox;

    @Inject
    public DbHelper(Box<DisponibilidadDTO> dispBox,
                    Box<DisponibilidadDetailsDTO> dispDetailsBox) {
        mDispBox = dispBox;
        mDispDetailsBox = dispDetailsBox;
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula) {
        Timber.i("Obteniendo disponibilidad de la cedula: %s", cedula);
        return Observable.create(subscriber -> {
            try {
                List<DisponibilidadDTO> disps = mDispBox.query()
                        .equal(DisponibilidadDTO_.cedula, cedula)
                        .build().find();
                subscriber.onNext(disps);
                subscriber.onComplete();
            } catch (Exception e) {
                Timber.e(e);
                subscriber.onError(e);
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia) {
        Timber.i("Obteniendo disponibilidad de la cedula: "+ cedula + " para el dia: " +idDia);
        return Observable.create(subscriber -> {
            try {
                List<DisponibilidadDTO> disps = mDispBox.query()
                        .equal(DisponibilidadDTO_.cedula, cedula)
                        .equal(DisponibilidadDTO_.idDia, idDia)
                        .build().find();
                subscriber.onNext(disps);
                subscriber.onComplete();
            } catch (Exception e) {
                Timber.e(e);
                subscriber.onError(e);
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula) {
        Timber.i("Obteniendo el detalle de la disponibilidad de la cedula: %s", cedula);
        return Observable.create(subscriber -> {
            try{
                DisponibilidadDetailsDTO disps = mDispDetailsBox.query()
                        .equal(DisponibilidadDetailsDTO_.cedula, cedula)
                        .build().findFirst();
                subscriber.onNext(disps);
                subscriber.onComplete();
            } catch (Exception e){
                Timber.e(e);
                subscriber.onError(e);
                subscriber.onComplete();
            }
        });
    }

    @Override
    public void saveDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades) {
        Timber.i("Guardando la disponibilidad");
        if (disponibilidades != null)
            mDispBox.put(disponibilidades);
    }

    @Override
    public void saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        Timber.i("Guardando el detalle de la disponibilidad");
        mDispDetailsBox.put(disponibilidadDetailsDTO);
    }

    @Override
    public void removeDisponibilidadLocal(int cedula) {
        Timber.i("Eliminando la disponibilidad para la cedula:%s", cedula);
        mDispBox.query().equal(DisponibilidadDTO_.cedula, cedula).build().remove();
    }

    @Override
    public void removeDisponibilidadLocal(int cedula, int idDia) {
        Timber.i("Eliminando disponibilidad de la cedula: "+ cedula + " para el dia: " +idDia);
        mDispBox.query()
                .equal(DisponibilidadDTO_.cedula, cedula)
                .equal(DisponibilidadDTO_.idDia, idDia)
                .build().remove();
    }

    @Override
    public void removeDisponibilidadDetailsLocal(int cedula) {
        Timber.i("Eliminando el detalle de la la disponibilidad para la cedula:%s", cedula);
        mDispDetailsBox.query().equal(DisponibilidadDetailsDTO_.cedula, cedula).build().remove();
    }
}
