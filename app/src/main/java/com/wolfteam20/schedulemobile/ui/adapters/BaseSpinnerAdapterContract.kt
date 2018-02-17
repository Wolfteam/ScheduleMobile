package com.wolfteam20.schedulemobile.ui.adapters

import android.view.View
import android.view.ViewGroup

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
interface BaseSpinnerAdapterContract<TItem> {
    fun setItems(items: MutableList<TItem>)
    fun getItem(itemID: Long): TItem
    fun getPosition(itemID: Long): Int
    fun createItemView(position: Int, convertView: View?, parent: ViewGroup?): View
}