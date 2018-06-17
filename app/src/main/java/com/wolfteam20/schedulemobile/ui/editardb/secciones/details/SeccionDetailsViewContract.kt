package com.wolfteam20.schedulemobile.ui.editardb.secciones.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SeccionDetailsViewContract : ItemDetailsBaseViewContract {
    fun setMateriaSpinnerItems(materias: MutableList<MateriaDetailsDTO>)
}