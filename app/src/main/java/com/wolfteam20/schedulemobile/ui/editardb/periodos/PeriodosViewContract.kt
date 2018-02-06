package com.wolfteam20.schedulemobile.ui.editardb.periodos

import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBViewContract

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface PeriodosViewContract : EditarDBViewContract {
    fun showList(periodos: MutableList<PeriodoAcademicoDTO>)
}