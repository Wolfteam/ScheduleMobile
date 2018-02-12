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

    void addDisponibilidadLocal(List<DisponibilidadDTO> disponibilidades);

    void addDisponibilidadDetailsLocal(DisponibilidadDetailsDTO disponibilidadDetailsDTO);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula);

    Observable<List<DisponibilidadDTO>> getDisponibilidadLocal(int cedula, int idDia);

    Observable<DisponibilidadDetailsDTO> getDisponibilidadDetailsLocal(int cedula);

    void removeDisponibilidadLocal(int cedula);

    void removeDisponibilidadLocal(int cedula, int idDia);

    void removeDisponibilidadDetailsLocal(int cedula);

    void addAulasLocal(List<AulaDetailsDTO> aulas);

    Observable<List<AulaDetailsDTO>> getAllAulasLocal();

    Single<AulaDetailsDTO> getAulaLocal(long idAula);

    void removeAulaLocal(long idAula);

    void removeAulasLocal();

    void addAulaLocal(AulaDetailsDTO aula);

    void updateAulaLocal(AulaDetailsDTO aula);
}
