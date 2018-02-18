package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_common_item_selected_overlay.view.*
import kotlinx.android.synthetic.main.editardb_fragment_aulas_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
class AulasListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<AulaDetailsDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val aulaView = inflater.inflate(R.layout.editardb_fragment_aulas_list_item, parent, false)
        // Return a new holder instance
        return AulasListViewHolder(aulaView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].idAula
    }

    inner class AulasListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: AulaDetailsDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                val nombreAula =
                    if (item.nombreAula.length > 5) item.nombreAula else "Aula: ${item.nombreAula}"
                val tipo =
                    if (item.tipoAula.nombreTipo.length > 7) "Laboratorio" else item.tipoAula.nombreTipo

                aula_list_item_nombre.text = nombreAula
                aula_list_item_capacidad.text = String.format("Capacidad: %d", item.capacidad)
                aula_list_item_tipo.text = tipo
                editardb_common_item_selected_overlay.visibility =
                        if (isItemSelected) View.VISIBLE else View.INVISIBLE
            }
    }
}