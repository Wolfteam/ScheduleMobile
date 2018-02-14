package com.wolfteam20.schedulemobile.ui.adapters

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */
interface SelectableAdapterContract {
    fun clearSelection()
    fun isSelected(position: Int): Boolean
    fun getSelectedItems(): ArrayList<Int>
    fun getSelectedItemCount(): Int
    fun toggleSelection(position: Int)
}