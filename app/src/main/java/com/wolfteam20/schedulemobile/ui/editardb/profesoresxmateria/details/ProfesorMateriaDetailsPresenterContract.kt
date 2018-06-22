package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.details

import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
interface ProfesorMateriaDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(idRelacion: Long, position: Int, model: ProfesorMateriaDetailsDTO?)
    fun delete()
    fun add(relacion: ProfesorMateriaDetailsDTO)
    fun update(relacion: ProfesorMateriaDetailsDTO)
}