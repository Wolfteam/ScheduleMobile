package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
class TipoAulaMateriaSpinnerAdapter(context: Context, layout: Int) :
    BaseSpinnerAdapter<TipoAulaMateriaDTO>(context, layout) {

    override fun getItemId(position: Int): Long {
        return mItemsList[position].idTipo
    }

   override fun getItem(itemID: Long): TipoAulaMateriaDTO {
        return mItemsList.first { it.idTipo == itemID }
    }

    override fun getPosition(itemID: Long): Int {
        return mItemsList
            .takeWhile { it.idTipo != itemID }
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

        holder.tvTextToShow?.text = mItemsList[position].nombreTipo
        return rowView
    }
}