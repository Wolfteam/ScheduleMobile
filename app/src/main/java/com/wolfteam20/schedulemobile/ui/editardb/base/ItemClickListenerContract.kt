package com.wolfteam20.schedulemobile.ui.editardb.base

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface ItemClickListenerContract {
    /**
     * Evento que ocurre al hacer click sobre algun item de una recycler view
     * @param itemID Id del objeto sobre el que se hizo click
     * @param itemPosition Posicion del item en la recycler view
     */
    fun onItemClicked(itemID: Long, itemPosition: Int)

    /**
     * Evento que ocurre en una pulsacion larga
     * @param itemPosition Posicion del item en la recycler view
     */
    fun onItemLongClicked(itemPosition: Int): Boolean
}