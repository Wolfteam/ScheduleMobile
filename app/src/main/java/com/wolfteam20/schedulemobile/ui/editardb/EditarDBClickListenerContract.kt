package com.wolfteam20.schedulemobile.ui.editardb

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface EditarDBClickListenerContract {
    fun onItemClicked(id: Long)
    fun onItemLongClicked(position: Int): Boolean
}