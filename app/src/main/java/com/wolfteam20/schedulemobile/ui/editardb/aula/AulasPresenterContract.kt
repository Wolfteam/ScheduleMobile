package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface AulasPresenterContract : ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: AulaDetailsDTO)
    fun deleteItems(aulas: MutableList<AulaDetailsDTO>)
    fun onItemAdded(itemID: Long)
    fun onItemUpdated(item: AulaDetailsDTO, itemPosition: Int)
}