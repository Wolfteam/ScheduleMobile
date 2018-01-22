package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
interface DispDetailsViewContract : BaseViewContract {
    /**
     * Agrega y muestra uuna [disponibilidad] mediante el adapter
     */
    fun addItem(disponibilidad: DisponibilidadDTO)

    /**
     * Obtiene todas las disponibilidades del adapter
     * @return MutableList<DisponibilidadDTO>
     */
    fun getItems(): MutableList<DisponibilidadDTO>

    /**
     * Agrega y muestra una lista de [disponibilidades] mediante el adapter
     */
    fun showList(disponibilidades: List<DisponibilidadDTO>)
}