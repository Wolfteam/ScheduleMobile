package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_common_item_selected_overlay.view.*
import kotlinx.android.synthetic.main.editardb_fragment_materias_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
class MateriasListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<MateriaDetailsDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val materiaView =
            inflater.inflate(R.layout.editardb_fragment_materias_list_item, parent, false)
        // Return a new holder instance
        return MateriasListViewHolder(materiaView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].codigo
    }

    inner class MateriasListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: MateriaDetailsDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                val nombreSemestre =
                    if (item.semestre.nombreSemestre.length > 1)
                        item.semestre.nombreSemestre
                    else
                        "Semestre: ${item.semestre.nombreSemestre}"
                val tipo =
                    if (item.tipoMateria.nombreTipo.length > 7)
                        "Laboratorio"
                    else
                        item.tipoMateria.nombreTipo

                materia_list_item_codigo.text = String.format("Codigo: %s", item.codigo)
                materia_list_item_nombre.text = item.asignatura
                materia_list_item_semestre.text = nombreSemestre
                materia_list_item_tipo.text = tipo
                editardb_common_item_selected_overlay.visibility =
                        if (isItemSelected) View.VISIBLE else View.INVISIBLE
            }
    }
}