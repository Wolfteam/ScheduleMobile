package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface ProfesorMateriaPresenterContract : ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: ProfesorMateriaDetailsDTO)
    fun deleteItems(items: MutableList<ProfesorMateriaDetailsDTO>)
    fun onItemAdded()
    fun onItemUpdated(item: ProfesorMateriaDetailsDTO, itemPosition: Int)
}