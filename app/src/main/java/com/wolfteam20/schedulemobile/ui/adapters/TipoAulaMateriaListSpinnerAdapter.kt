package com.wolfteam20.schedulemobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
class TipoAulaMateriaListSpinnerAdapter(context: Context, layout: Int) :
    ArrayAdapter<TipoAulaMateriaDTO>(context, layout) {

    private val mContext: Context = context
    private val mInflater: LayoutInflater
    private var mTipos: MutableList<TipoAulaMateriaDTO> = arrayListOf()
    private val mLayout: Int = layout

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
        return mTipos.size
    }

    override fun getItem(position: Int): TipoAulaMateriaDTO {
        return mTipos[position]
    }

    override fun getItemId(position: Int): Long {
        return mTipos[position].idTipo
    }

    fun getItem(idTipo: Long): TipoAulaMateriaDTO {
        return mTipos.first { it.idTipo == idTipo }
    }

    fun getPosition(idTipo: Long): Int {
        return mTipos
            .takeWhile { it.idTipo != idTipo }
            .count()
    }

    fun setItems(tipos: MutableList<TipoAulaMateriaDTO>) {
        mTipos = tipos
        notifyDataSetChanged()
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        val holder: ViewHolder

        if (convertView == null) {
            rowView = mInflater.inflate(mLayout, parent, false)
            holder = ViewHolder()
            holder.tvNombeTipo = rowView.findViewById(R.id.tv_editardb_details_spinner) as TextView
            rowView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            rowView = convertView
        }

        holder.tvNombeTipo?.text = mTipos[position].nombreTipo
        return rowView
    }

    internal class ViewHolder {
        var tvNombeTipo: TextView? = null
    }
}