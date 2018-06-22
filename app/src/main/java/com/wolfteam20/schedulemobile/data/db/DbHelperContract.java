package com.wolfteam20.schedulemobile.data.db;

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

public interface DbHelperContract {

    void addDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades);

    void addDisponibilidadDetailsLocal(@NotNull DisponibilidadDetailsDTO disponibilidadDetailsDTO);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia);

    Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula);

    void removeDisponibilidadLocal(int cedula);

    void removeDisponibilidadLocal(int cedula, int idDia);

    void removeDisponibilidadDetailsLocal(int cedula);
}
