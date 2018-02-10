package com.wolfteam20.schedulemobile.data.db;

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

public interface DbHelperContract {
    Observable<List<AulaDetailsDTO>> getAllAulasLocal();

    Single<AulaDetailsDTO> getAulaLocal(long idAula);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia);

    Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula);

    void saveAulasLocal(List<AulaDetailsDTO> aulas);

    void saveDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades);

    void saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO);

    void removeAulaLocal(long idAula);

    void removeAulasLocal();

    void removeDisponibilidadLocal(int cedula);

    void removeDisponibilidadLocal(int cedula, int idDia);

    void removeDisponibilidadDetailsLocal(int cedula);
}
