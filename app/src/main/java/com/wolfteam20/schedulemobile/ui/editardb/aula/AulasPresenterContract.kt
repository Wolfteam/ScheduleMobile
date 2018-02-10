package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
interface AulasPresenterContract : BasePresenterContract {
    fun subscribe()
    fun onItemClicked(id:Long)
    fun onItemLongClicked(position: Int)
    fun onFABAddClicked()
    fun onFABDeleteClicked(aulas : MutableList<AulaDetailsDTO>)
}