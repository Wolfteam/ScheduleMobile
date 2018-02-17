package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_fragment_profesores_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class ProfesoresListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<ProfesorDetailsDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesoresListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val profesorView = inflater.inflate(R.layout.editardb_fragment_profesores_list_item, parent, false)
        // Return a new holder instance
        return ProfesoresListViewHolder(profesorView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].cedula
    }

    inner class ProfesoresListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: ProfesorDetailsDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                profesor_list_item_cedula.text = String.format("Cedula: %s", item.cedula)
                profesor_list_item_fullname.text =
                        String.format("%s %s", item.nombre, item.apellido)
                profesor_list_item_horas_a_cumplir.text =
                        String.format("Horas a cumplir: %s", item.prioridad?.horasACumplir)
                profesor_list_item_prioridad.text =
                        String.format("Prioridad: %s", item.prioridad?.codigoPrioridad)
            }
    }
}