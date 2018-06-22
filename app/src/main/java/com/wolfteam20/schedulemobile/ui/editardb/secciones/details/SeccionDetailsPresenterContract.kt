package com.wolfteam20.schedulemobile.ui.editardb.secciones.details

import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
interface SeccionDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(codigo: Long, position: Int, model: SeccionDetailsDTO?)
    fun delete()
    fun add(seccion: SeccionDetailsDTO)
    fun update(seccion: SeccionDetailsDTO)
}