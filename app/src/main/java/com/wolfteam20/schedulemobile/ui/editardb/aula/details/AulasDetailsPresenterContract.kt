package com.wolfteam20.schedulemobile.ui.editardb.aula.details

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
interface AulasDetailsPresenterContract : BasePresenterContract {
    fun subscribe(idAula: Long, position: Int, model:AulaDetailsDTO?)
    fun onCancelClicked()
    fun onDeleteClicked()
    fun onSaveClicked()
    fun deleteAula()
    fun addAula(aula: AulaDetailsDTO)
    fun updateAula(aula: AulaDetailsDTO)
}