package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract

/**
 * Created by Efrain.Bastidas on 14/2/2018.
 */
abstract class BaseItemListAdapter<TItem> : SelectableAdapter<BaseItemListAdapter<TItem>.ItemViewHolder>(),
    BaseItemListAdapterContract<TItem> {

    /**
     * MutableList de [TItem]
     */
    protected var mItemList: MutableList<TItem> = mutableListOf()

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            val item = mItemList[position]
            val isItemSelected = isSelected(position)
            holder.bind(item, position, isItemSelected)
        }
    }

    override fun addItem(item: TItem) {
        mItemList.add(item)
        notifyItemInserted(mItemList.size - 1)
    }

    override fun getItem(itemPosition: Int): TItem {
        return mItemList[itemPosition]
    }

    override fun getItems(itemsPosition: ArrayList<Int>): MutableList<TItem> {
        return itemsPosition.map { mItemList[it] }.toMutableList()
    }

    override fun setItems(items: MutableList<TItem>) {
        mItemList = items
        notifyDataSetChanged()
    }

    override fun updateItem(itemsPosition: Int, item: TItem) {
        mItemList[itemsPosition] = item
        notifyItemChanged(itemsPosition)
    }

    override fun removeItem(itemsPosition: Int) {
        mItemList.removeAt(itemsPosition)
        notifyItemRemoved(itemsPosition)
    }

    override fun removeItems(positions: ArrayList<Int>) {
        //Esto para evitar que se queden items seleccionados
        clearSelection()
        positions.sortDescending()
        while (!positions.isEmpty()) {
            if (positions.size == 1) {
                removeItem(positions[0])
                positions.removeAt(0)
            } else {
                var count = 1
                //Este while sirve para casos donde seleccionas multiples items posiciones seguidas
                //ee.g(5,4,3)
                while (positions.size > count && positions[count] == positions[count - 1] - 1) {
                    ++count
                }
                if (count == 1)
                    removeItem(positions[0])
                else
                    removeRange(positions[count - 1], count)

                for (i in 0 until count)
                    positions.removeAt(0)
            }
        }
    }

    override fun removeRange(positionStart: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            mItemList.removeAt(positionStart)
        }
        notifyItemRangeRemoved(positionStart, itemCount)
    }


    abstract inner class ItemViewHolder(root: View, clickListener: ItemClickListenerContract) :
        RecyclerView.ViewHolder(root) {

        private val mClickListener = clickListener

        init {
            root.setOnClickListener {
                mClickListener.onItemClicked(getItemId(layoutPosition), layoutPosition)
            }
            root.setOnLongClickListener {
                return@setOnLongClickListener mClickListener.onItemLongClicked(layoutPosition)
            }
        }

        abstract fun bind(item: TItem, itemPosition: Int, isItemSelected: Boolean)
    }
}