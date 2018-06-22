package com.wolfteam20.schedulemobile.ui.editardb.usuarios.details

import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
interface UsuarioDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(cedula: Long, position: Int, model: UsuarioDetailsDTO?)
    fun delete()
    fun add(usuario: UsuarioDetailsDTO)
    fun update(usuario: UsuarioDetailsDTO)
}