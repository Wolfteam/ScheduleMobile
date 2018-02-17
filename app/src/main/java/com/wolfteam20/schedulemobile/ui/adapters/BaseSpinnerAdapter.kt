package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
abstract class BaseSpinnerAdapter<TItem>(context: Context, resource: Int) :
    ArrayAdapter<TItem>(context, resource), BaseSpinnerAdapterContract<TItem> {

    private val mContext: Context = context
    protected val mInflater: LayoutInflater
    protected var mItemsList: MutableList<TItem> = arrayListOf()
    protected val mLayout: Int = resource

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return mItemsList.size
    }

    override fun getItem(position: Int): TItem {
        return mItemsList[position]
    }

    override fun setItems(items: MutableList<TItem>) {
        mItemsList = items
        notifyDataSetChanged()
    }

    protected class ViewHolder {
        var tvTextToShow: TextView? = null
    }
}