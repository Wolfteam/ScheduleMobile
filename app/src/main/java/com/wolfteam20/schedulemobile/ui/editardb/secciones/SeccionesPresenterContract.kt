package com.wolfteam20.schedulemobile.ui.editardb.secciones

import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface SeccionesPresenterContract : ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: SeccionDetailsDTO)
    fun deleteItems(items: MutableList<SeccionDetailsDTO>)
    fun onItemAdded(item: SeccionDetailsDTO)
    fun onItemUpdated(item: SeccionDetailsDTO, itemPosition: Int)
}