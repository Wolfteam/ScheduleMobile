package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria

import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface ProfesorMateriaViewContract :
    ItemBaseViewContract {
    fun showList(pm: MutableList<ProfesorMateriaDetailsDTO>)
}