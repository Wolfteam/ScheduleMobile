package com.wolfteam20.schedulemobile.data.db;

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

public interface DbHelperContract {
    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia);

    Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula);

    void saveDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades);

    void saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO);

    void removeDisponibilidadLocal(int cedula);

    void removeDisponibilidadLocal(int cedula, int idDia);

    void removeDisponibilidadDetailsLocal(int cedula);
}
