package com.wolfteam20.schedulemobile.ui.editardb.aula.details

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenterContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
interface AulaDetailsPresenterContract : ItemDetailsBasePresenterContract {
    fun subscribe(idAula: Long, position: Int, model:AulaDetailsDTO?)
    fun delete()
    fun add(aula: AulaDetailsDTO)
    fun update(aula: AulaDetailsDTO)
}