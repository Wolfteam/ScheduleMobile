package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.PrivilegioDTO

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class PrivilegioSpinnerAdapter(context: Context, resource: Int) :
    BaseSpinnerAdapter<PrivilegioDTO>(context, resource) {

    override fun getItemId(position: Int): Long {
        return mItemsList[position].idPrivilegio
    }

    override fun getItem(itemID: Long): PrivilegioDTO {
        return mItemsList.first { it.idPrivilegio == itemID }
    }

    override fun getPosition(itemID: Long): Int {
        return mItemsList.takeWhile { it.idPrivilegio != itemID }.count()
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

        holder.tvTextToShow?.text = mItemsList[position].nombrePrivilegio
        return rowView
    }
}