package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface AulasViewContract : ItemBaseViewContract {
    fun showList(aulas: MutableList<AulaDetailsDTO>)

    //@StateStrategyType(OneExecutionStateStrategy::class)
    fun addItem(aula: AulaDetailsDTO)

    //@StateStrategyType(OneExecutionStateStrategy::class)
    fun updateItem(position: Int, aula: AulaDetailsDTO)
}