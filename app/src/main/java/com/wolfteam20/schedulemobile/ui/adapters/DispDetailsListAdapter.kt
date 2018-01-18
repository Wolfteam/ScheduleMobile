package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import kotlinx.android.synthetic.main.disponibilidad_details_list_item.view.*


/**
 * Created by Efrain.Bastidas on 1/15/2018.
 */
class DispDetailsListAdapter(horas: Array<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDispList: MutableList<DisponibilidadDTO> = mutableListOf()
    private var mHoras = horas

    class DispDetailsListViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind(disponibilidad: DisponibilidadDTO, text : String, position: Int) = with(itemView) {
            disp_details_from_to_hours.text = text
//            disp_details_delete.setOnClickListener {
//                Toast.makeText(rootView.context, "Presionaste el icono en la posicion $position",Toast.LENGTH_LONG).show()
//            }
        }
    }

    override fun getItemCount(): Int {
        return mDispList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Que bestia que las propiedades de holder sean inferidas dentro del if
        if (holder is DispDetailsListViewHolder) {
            val disponibilidad = mDispList[position]
            holder.bind(disponibilidad, getTextToShow(disponibilidad.idHoraInicio, disponibilidad.idHoraFin), position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val dispDetailsView = inflater.inflate(R.layout.disponibilidad_details_list_item, parent, false)
        // Return a new holder instance
        return DispDetailsListViewHolder(dispDetailsView)
    }

    fun addDisponibilidades(disponibilidades: List<DisponibilidadDTO>) {
        mDispList.clear()
        mDispList.addAll(disponibilidades)
        notifyDataSetChanged()
    }

    private fun getTextToShow(idHoraInicio: Int, idHoraFin: Int) : String {
        return "${mHoras[idHoraInicio - 1]} a ${mHoras[idHoraFin - 1]}"
    }
}
