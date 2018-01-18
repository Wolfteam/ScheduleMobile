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
        if (disponibilidades != null)
            mDispBox.put(disponibilidades);
    }

    @Override
    public void saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        mDispDetailsBox.put(disponibilidadDetailsDTO);
    }

    @Override
    public void removeDisponibilidadLocal(int cedula) {
        mDispBox.query().equal(DisponibilidadDTO_.cedula, cedula).build().remove();
    }

    @Override
    public void removeDisponibilidadLocal(int cedula, int idDia) {
        mDispBox.query()
                .equal(DisponibilidadDTO_.cedula, cedula)
                .equal(DisponibilidadDTO_.idDia, idDia)
                .build().remove();
    }

    @Override
    public void removeDisponibilidadDetailsLocal(int cedula) {
        mDispDetailsBox.query().equal(DisponibilidadDetailsDTO_.cedula, cedula).build().remove();
    }
}
