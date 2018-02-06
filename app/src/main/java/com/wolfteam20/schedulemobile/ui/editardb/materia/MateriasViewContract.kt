package com.wolfteam20.schedulemobile.ui.editardb.materia

import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface MateriasViewContract : EditarDBViewContract {
    fun showList(materias: MutableList<MateriaDetailsDTO>)
}