package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.arellomobile.mvp.viewstate.strategy.*
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AulasViewContract : EditarDBViewContract {
    fun showList(aulas: MutableList<AulaDetailsDTO>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeSelectedListItems()

    fun startActionMode()

    @StateStrategyType(SkipStrategy::class)
    fun showConfirmDelete()
}