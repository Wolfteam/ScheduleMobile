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
class ProfesoresListSpinnerAdapter : ArrayAdapter<ProfesorDetailsDTO> {
    private val mContext: Context
    private val mInflater: LayoutInflater
    private val mProfesores: MutableList<ProfesorDetailsDTO>
    private val mLayout : Int

    constructor(context: Context, layout: Int, profesores: MutableList<ProfesorDetailsDTO>)
            : super(context, layout, profesores) {
        mContext = context
        mInflater = LayoutInflater.from(mContext)
        mProfesores = profesores
        mLayout = layout
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createItemView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return mProfesores.size
    }

    override fun getItem(position: Int): ProfesorDetailsDTO {
        return mProfesores[position]
    }

    override fun getItemId(position: Int): Long {
        return mProfesores[position].cedula.toLong()
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup?) : View{
        val rowView: View
        val holder: ViewHolder

        if (convertView == null) {
            rowView = mInflater.inflate(mLayout, parent, false)
            holder = ViewHolder()
            holder.tvNombreProfesor = rowView.findViewById(R.id.profesorFullname) as TextView
            rowView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            rowView = convertView
        }

        val profesor = mProfesores[position]
        val fullname = "${profesor.nombre} ${profesor.apellido}"
        holder.tvNombreProfesor?.text = fullname
        return rowView
    }

    internal class ViewHolder {
        var tvNombreProfesor: TextView? = null
    }
}