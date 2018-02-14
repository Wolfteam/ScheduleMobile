package com.wolfteam20.schedulemobile.ui.editardb.materia

import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface MateriasViewContract :
    ItemBaseViewContract {
    fun showList(materias: MutableList<MateriaDetailsDTO>)
}