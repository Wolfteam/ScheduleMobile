package com.wolfteam20.schedulemobile.ui.editardb.base

import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */
interface ItemBasePresenterContract : BasePresenterContract {
    fun onItemLongClicked(itemPosition: Int)
    fun onFABAddClicked()
    fun onFABDeleteClicked(itemsSelected: Int)
    fun onActionMode()
    fun onToggleItemSelection(itemsToSelect: Int)
}