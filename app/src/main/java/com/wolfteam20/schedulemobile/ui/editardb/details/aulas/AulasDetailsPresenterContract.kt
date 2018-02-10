package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
interface AulasDetailsPresenterContract : BasePresenterContract {
    fun subscribe(idAula: Long)
    fun onDeleteClicked()
    fun onSaveClicked()
}