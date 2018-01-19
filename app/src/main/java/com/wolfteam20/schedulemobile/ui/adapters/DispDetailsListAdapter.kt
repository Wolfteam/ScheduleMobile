package com.wolfteam20.schedulemobile.ui.adapters

import android.graphics.Color
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
class DispDetailsListAdapter(horas: Array<String>) : SelectableAdapter<DispDetailsListAdapter.DispDetailsListViewHolder>() {

    private var mDispList: MutableList<DisponibilidadDTO> = mutableListOf()
    private var mHoras = horas
    // an array of selected items (Integer indices)
    private var mSelectedList: ArrayList<Int> = arrayListOf()


    class DispDetailsListViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        fun bind(disponibilidad: DisponibilidadDTO, text: String, position: Int) = with(itemView) {
            disp_details_from_to_hours.text = text
//            disp_details_delete.setOnClickListener {
//                Toast.makeText(rootView.context, "Presionaste el icono en la posicion $position",Toast.LENGTH_LONG).show()
//            }
        }

    }

    override fun getItemCount(): Int {
        return mDispList.size
    }

    override fun onBindViewHolder(holder: DispDetailsListViewHolder?, position: Int) {
        if (holder is DispDetailsListViewHolder) {
            val disponibilidad = mDispList[position]
            holder.bind(disponibilidad, getTextToShow(disponibilidad.idHoraInicio, disponibilidad.idHoraFin), position)
        }
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        //Que bestia que las propiedades de holder sean inferidas dentro del if
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispDetailsListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val dispDetailsView = inflater.inflate(R.layout.disponibilidad_details_list_item, parent, false)
        dispDetailsView.setOnLongClickListener(
                { v ->
                    v.setBackgroundColor(Color.CYAN)

                    return@setOnLongClickListener true
                })
        // Return a new holder instance
        return DispDetailsListViewHolder(dispDetailsView)
    }

    fun addDisponibilidades(disponibilidades: List<DisponibilidadDTO>) {
        mDispList.clear()
        mDispList.addAll(disponibilidades)
        notifyDataSetChanged()
    }

    private fun getTextToShow(idHoraInicio: Int, idHoraFin: Int): String {
        return "${mHoras[idHoraInicio - 1]} a ${mHoras[idHoraFin - 1]}"
    }
}
