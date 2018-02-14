package com.wolfteam20.schedulemobile.data.db;

import com.wolfteam20.schedulemobile.data.db.Repository.DisponibilidadDetailsRepository;
import com.wolfteam20.schedulemobile.data.db.Repository.DisponibilidadRepository;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

public class DbHelper implements DbHelperContract {
    private DisponibilidadRepository mDispRepository;
    private DisponibilidadDetailsRepository mDispDetailsRepository;

    @Inject
    public DbHelper(
            DisponibilidadRepository disponibilidadRepository,
            DisponibilidadDetailsRepository disponibilidadDetailsRepository) {
        mDispRepository = disponibilidadRepository;
        mDispDetailsRepository = disponibilidadDetailsRepository;
    }

    @Override
    public void addDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades) {
        Timber.i("Guardando la disponibilidad");
        if (disponibilidades.size() > 0)
            mDispRepository.addRange(disponibilidades);
    }

    @Override
    public void addDisponibilidadDetailsLocal(@NotNull DisponibilidadDetailsDTO disponibilidadDetailsDTO) {
        Timber.i("Guardando el detalle de la disponibilidad");
        mDispDetailsRepository.add(disponibilidadDetailsDTO);
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula) {
        Timber.i("Obteniendo disponibilidad de la cedula: %s", cedula);
        return mDispRepository.getByCedula(cedula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia) {
        Timber.i("Obteniendo disponibilidad de la cedula: " + cedula + " para el dia: " + idDia);
        return mDispRepository.getByCedulaDia(cedula, idDia)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula) {
        Timber.i("Obteniendo el detalle de la disponibilidad de la cedula: %s", cedula);
        return mDispDetailsRepository.getByCedula(cedula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void removeDisponibilidadLocal(int cedula) {
        Timber.i("Eliminando la disponibilidad para la cedula:%s", cedula);
        mDispRepository.removeByCedula(cedula);
    }

    @Override
    public void removeDisponibilidadLocal(int cedula, int idDia) {
        Timber.i("Eliminando disponibilidad de la cedula: " + cedula + " para el dia: " + idDia);
        mDispRepository.removeByCedulaDia(cedula, idDia);
    }

    @Override
    public void removeDisponibilidadDetailsLocal(int cedula) {
        Timber.i("Eliminando el detalle de la la disponibilidad para la cedula:%s", cedula);
        mDispDetailsRepository.removeByCedula(cedula);
    }
}
