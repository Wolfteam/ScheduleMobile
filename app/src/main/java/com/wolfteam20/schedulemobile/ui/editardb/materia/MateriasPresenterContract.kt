package com.wolfteam20.schedulemobile.ui.editardb.materia

import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface MateriasPresenterContract : ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: MateriaDetailsDTO)
    fun deleteItems(materias: MutableList<MateriaDetailsDTO>)
    fun onItemAdded(item: MateriaDetailsDTO)
    fun onItemUpdated(item: MateriaDetailsDTO, itemPosition: Int)
}