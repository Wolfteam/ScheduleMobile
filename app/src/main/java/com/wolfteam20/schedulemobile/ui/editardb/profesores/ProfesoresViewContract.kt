package com.wolfteam20.schedulemobile.ui.editardb.profesores

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
interface ProfesoresViewContract : EditarDBViewContract {
    fun showList(profesores: MutableList<ProfesorDetailsDTO>)
}