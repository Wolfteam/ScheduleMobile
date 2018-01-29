package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
interface DispDetailsViewContract : BaseViewContract {
    /**
     * Agrega y muestra uuna [disponibilidad] mediante el adapter
     */
    @StateStrategyType(SkipStrategy::class)
    fun addItem(disponibilidad: DisponibilidadDTO)

    /**
     * Agrega y muestra una lista de [disponibilidades] mediante el adapter
     */
    fun showList(disponibilidades: MutableList<DisponibilidadDTO>)
}