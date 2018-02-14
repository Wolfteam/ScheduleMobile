package com.wolfteam20.schedulemobile.data.db.Repository

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO_
import io.objectbox.Box
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
class DisponibilidadDetailsRepository(box: Box<DisponibilidadDetailsDTO>) :
    Repository<DisponibilidadDetailsDTO>(box) {

    fun getByCedula(cedula: Long) : Observable<DisponibilidadDetailsDTO> {
        return Observable.create{ subscriber ->
            try {
                val disps = mBox.query()
                    .equal(DisponibilidadDetailsDTO_.cedula, cedula)
                    .build().findFirst()
                if (disps == null)
                    subscriber.onComplete()
                else
                    subscriber.onNext(disps)
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
                subscriber.onComplete()
            }
        }
    }

    fun removeByCedula(cedula: Long) {
        mBox.query().equal(DisponibilidadDetailsDTO_.cedula, cedula).build().remove()
    }
}