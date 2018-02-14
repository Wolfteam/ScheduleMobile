package com.wolfteam20.schedulemobile.ui.editardb.secciones

import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface SeccionesViewContract :
    ItemBaseViewContract {
    fun showList(secciones : MutableList<SeccionDetailsDTO>)
}