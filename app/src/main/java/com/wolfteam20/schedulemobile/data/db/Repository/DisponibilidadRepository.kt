package com.wolfteam20.schedulemobile.data.db.Repository

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO_
import io.objectbox.Box
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
class DisponibilidadRepository(box: Box<DisponibilidadDTO>) : Repository<DisponibilidadDTO>(box) {

    fun getByCedula(cedula: Long): Observable<List<DisponibilidadDTO>> {
        return Observable.create { subscriber ->
            try {
                val disps = mBox.query()
                    .equal(DisponibilidadDTO_.cedula, cedula)
                    .build().find()
                subscriber.onNext(disps)
                subscriber.onComplete()
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
                subscriber.onComplete()
            }
        }
    }

    fun getByCedulaDia(cedula: Long, idDia: Long): Observable<List<DisponibilidadDTO>> {
        return Observable.create { subscriber ->
            try {
                val disps = mBox.query()
                    .equal(DisponibilidadDTO_.cedula, cedula)
                    .equal(DisponibilidadDTO_.idDia, idDia)
                    .build().find()
                subscriber.onNext(disps)
                subscriber.onComplete()
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
                subscriber.onComplete()
            }
        }
    }

    fun removeByCedula(cedula: Long) {
        mBox.query().equal(DisponibilidadDTO_.cedula, cedula).build().remove()
    }

    fun removeByCedulaDia(cedula: Long, idDia: Long) {
        mBox.query()
            .equal(DisponibilidadDTO_.cedula, cedula)
            .equal(DisponibilidadDTO_.idDia, idDia)
            .build().remove()
    }
}