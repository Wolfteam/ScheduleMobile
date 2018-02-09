package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface AulasViewContract : EditarDBViewContract {
    fun showList(aulas : MutableList<AulaDetailsDTO>)
    fun removeSelectedListItems()
}