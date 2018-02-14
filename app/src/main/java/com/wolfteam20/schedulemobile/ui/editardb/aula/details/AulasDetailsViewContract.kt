package com.wolfteam20.schedulemobile.ui.editardb.aula.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AulasDetailsViewContract :
    ItemDetailsBaseViewContract {
    fun showItem(aula: AulaDetailsDTO)
    fun setTipoAulaSpinnerItems(tipos: MutableList<TipoAulaMateriaDTO>)
}