package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.wolfteam20.schedulemobile.data.network.models.AulaDTO
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import java.text.ParsePosition

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
interface AulasDetailsPresenterContract : BasePresenterContract {
    fun subscribe(idAula: Long, position: Int)
    fun onCancelClicked()
    fun onDeleteClicked()
    fun onSaveClicked()
    fun deleteAula()
    fun addAula(aula: AulaDTO)
    fun updateAula(aula: AulaDTO)
}