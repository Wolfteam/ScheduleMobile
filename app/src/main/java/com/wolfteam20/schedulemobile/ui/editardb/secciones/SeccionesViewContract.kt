package com.wolfteam20.schedulemobile.ui.editardb.secciones

import com.wolfteam20.schedulemobile.data.network.models.SeccionesDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface SeccionesViewContract : EditarDBViewContract{
    fun showList(secciones : MutableList<SeccionesDetailsDTO>)
}