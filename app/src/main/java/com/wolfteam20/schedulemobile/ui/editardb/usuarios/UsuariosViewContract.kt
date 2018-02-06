package com.wolfteam20.schedulemobile.ui.editardb.usuarios

import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface UsuariosViewContract : EditarDBViewContract {
    fun showList(usuarios : MutableList<UsuarioDetailsDTO>)
}