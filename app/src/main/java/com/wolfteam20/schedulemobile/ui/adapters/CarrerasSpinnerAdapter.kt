package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.CarreraDTO

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
class CarrerasSpinnerAdapter(context: Context, resource: Int) :
    BaseSpinnerAdapter<CarreraDTO>(context, resource) {

    override fun getItemId(position: Int): Long {
        return mItemsList[position].idCarrera
    }

    override fun getItem(itemID: Long): CarreraDTO {
        return mItemsList.first { it.idCarrera == itemID }
    }

    override fun getPosition(itemID: Long): Int {
        return mItemsList
            .takeWhile { it.idCarrera != itemID }
            .count()
    }

    override fun createItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        val holder: ViewHolder

        if (convertView == null) {
            rowView = mInflater.inflate(mLayout, parent, false)
            holder = ViewHolder()
            holder.tvTextToShow = rowView.findViewById(R.id.tv_editardb_details_spinner) as TextView
            rowView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            rowView = convertView
        }

        holder.tvTextToShow?.text = mItemsList[position].nombreCarrera
        return rowView
    }
}