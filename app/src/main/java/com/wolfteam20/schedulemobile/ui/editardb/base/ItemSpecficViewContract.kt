package com.wolfteam20.schedulemobile.ui.editardb.base

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Efrain.Bastidas on 14/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ItemSpecficViewContract<TItem> : ItemBaseViewContract {
    fun showList(items: MutableList<TItem>)

    fun addItem(item: TItem)

    fun updateItem(position: Int, item: TItem)

    fun removeItem(position: Int)
}