package com.wolfteam20.schedulemobile.ui.editardb.profesores

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface ProfesoresViewContract :
    ItemBaseViewContract {
    fun showList(profesores: MutableList<ProfesorDetailsDTO>)
}