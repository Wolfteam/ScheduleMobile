package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray






/**
 * Created by Efrain.Bastidas on 1/19/2018.
 */
abstract class SelectableAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var mSelectedItems : SparseBooleanArray = SparseBooleanArray()

    /**
     * Clear the selection status for all items
     */
    fun clearSelection() {
        val selection = getSelectedItems()
        mSelectedItems.clear()
        for (i in selection) {
            notifyItemChanged(i)
        }
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    fun isSelected(position: Int): Boolean {
        return getSelectedItems().contains(position)
    }



    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    fun getSelectedItems(): List<Int> {
        val items : ArrayList<Int> = ArrayList(mSelectedItems.size())
        (0 until mSelectedItems.size()).mapTo(items) {
            mSelectedItems.keyAt(it)
        }
        return items
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    fun getSelectedItemCount(): Int {
        return mSelectedItems.size()
    }

    /**
     * Toggle the selection status of the item at a given position
     * @param position Position of the item to toggle the selection status for
     */
    fun toggleSelection(position: Int) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position)
        } else {
            mSelectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }

}

