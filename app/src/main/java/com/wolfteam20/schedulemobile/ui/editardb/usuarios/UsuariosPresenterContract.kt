package com.wolfteam20.schedulemobile.ui.editardb.usuarios

import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface UsuariosPresenterContract :ItemBasePresenterContract {
    fun subscribe()
    fun onItemClicked(itemID: Long, itemPosition: Int, item: UsuarioDetailsDTO)
    fun deleteItems(items: MutableList<UsuarioDetailsDTO>)
    fun onItemAdded(item: UsuarioDetailsDTO)
    fun onItemUpdated(item: UsuarioDetailsDTO, itemPosition: Int)
}