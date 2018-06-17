package com.wolfteam20.schedulemobile.ui.editardb.profesores.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.PrioridadProfesorDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfesorDetailsViewContract : ItemDetailsBaseViewContract {
    fun setPrioridadSpinnerItems(prioridades: MutableList<PrioridadProfesorDTO>)
}