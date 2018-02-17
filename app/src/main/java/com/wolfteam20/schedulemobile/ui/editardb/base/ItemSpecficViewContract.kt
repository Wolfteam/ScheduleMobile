package com.wolfteam20.schedulemobile.ui.editardb.base

import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain.Bastidas on 14/2/2018.
 */
interface ItemSpecficViewContract<TItem> : ItemBaseViewContract {
    fun showList(items: MutableList<TItem>)

    fun addItem(item: TItem)

    fun updateItem(position: Int, item: TItem)

}