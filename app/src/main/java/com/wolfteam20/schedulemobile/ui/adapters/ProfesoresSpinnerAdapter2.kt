package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO


/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
class ProfesoresSpinnerAdapter2(context: Context, layout: Int) :
    ProfesoresSpinnerAdapter(context, layout) {

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

        val profesor = mItemsList[position]
        val fullname = "${profesor.nombre} ${profesor.apellido}"
        holder.tvTextToShow?.text = fullname
        return rowView
    }
}