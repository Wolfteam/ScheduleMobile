package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.wolfteam20.schedulemobile.ui.base.BaseContractPresenter

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
interface DispDetailsPresenterContract<V : DispDetailsViewContract> : BaseContractPresenter<V> {

    fun addDisponibilidad(idHoraInicio : Int, idHoraFin : Int)

    fun saveDisponibilidadLocal()

    fun onDisponibilidadDeleted(idHoraInicio : Int, idHoraFin : Int)

    fun subscribe(cedula: Int, idDia: Int)

    /**
     * Valida que la [idHoraInicio] y la [idHoraFin] se encuentren en un rango correcto
     * y que no choque con alguna disponibilidad existente
     * @return True en caso de exito
     */
    fun validateHorasSelected(idHoraInicio : Int, idHoraFin : Int) : Boolean


}