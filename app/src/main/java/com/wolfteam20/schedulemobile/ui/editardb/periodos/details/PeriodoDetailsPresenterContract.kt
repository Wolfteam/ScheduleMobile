package com.wolfteam20.schedulemobile.ui.editardb.periodos.details

import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
interface PeriodoDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(idPeriodo: Long, position: Int, model: PeriodoAcademicoDTO?)
    fun delete()
    fun add(periodo: PeriodoAcademicoDTO)
    fun update(periodo: PeriodoAcademicoDTO)
}