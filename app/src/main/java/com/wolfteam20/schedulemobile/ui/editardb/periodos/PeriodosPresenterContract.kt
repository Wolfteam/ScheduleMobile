package com.wolfteam20.schedulemobile.ui.editardb.periodos

import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface PeriodosPresenterContract : ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: PeriodoAcademicoDTO)
    fun deleteItems(items: MutableList<PeriodoAcademicoDTO>)
    fun onItemAdded()
    fun onItemUpdated(item: PeriodoAcademicoDTO, itemPosition: Int)
}