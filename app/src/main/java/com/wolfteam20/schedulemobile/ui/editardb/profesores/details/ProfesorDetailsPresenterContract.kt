package com.wolfteam20.schedulemobile.ui.editardb.profesores.details

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
interface ProfesorDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(cedula: Long, position: Int, model: ProfesorDetailsDTO?)
    fun delete()
    fun add(profesor: ProfesorDetailsDTO)
    fun update(profesor: ProfesorDetailsDTO)
}