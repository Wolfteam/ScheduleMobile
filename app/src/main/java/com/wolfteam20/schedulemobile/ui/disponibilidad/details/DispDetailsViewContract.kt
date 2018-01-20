package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
interface DispDetailsViewContract : BaseViewContract {
    //fun addDisponibilidad(disponibilidad: MutableList<DisponibilidadDTO>)
    fun showEmptyList()
    fun showError(error: String)
    fun showList(disponibilidades: List<DisponibilidadDTO>)
    fun showMessage(msg : String)
}