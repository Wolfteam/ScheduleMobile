package com.wolfteam20.schedulemobile.ui.editardb.profesores

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface ProfesoresPresenterContract : ItemBasePresenterContract{
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: ProfesorDetailsDTO)
    fun deleteItems(items: MutableList<ProfesorDetailsDTO>)
    fun onItemAdded(item: ProfesorDetailsDTO)
    fun onItemUpdated(item: ProfesorDetailsDTO, itemPosition: Int)
}