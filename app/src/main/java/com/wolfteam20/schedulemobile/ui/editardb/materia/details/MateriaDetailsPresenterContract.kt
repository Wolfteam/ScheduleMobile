package com.wolfteam20.schedulemobile.ui.editardb.materia.details

import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
interface MateriaDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(codigo: Long, position: Int, model: MateriaDetailsDTO?)
    fun delete()
    fun add(materia: MateriaDetailsDTO)
    fun update(materia: MateriaDetailsDTO)
}