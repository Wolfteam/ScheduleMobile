package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.secciones_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
class SeccionesListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<SeccionDetailsDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeccionesListAdapter.SeccionesListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val seccionView = inflater.inflate(R.layout.secciones_fragment_list_item, parent, false)
        // Return a new holder instance
        return SeccionesListViewHolder(seccionView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].materia.codigo
    }

    inner class SeccionesListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: SeccionDetailsDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                secciones_list_item_alumnos.text =
                        String.format("%s alumno(s) c/u", item.cantidadAlumnos)
                secciones_list_item_codigo.text =
                        String.format("Codigo: %s", item.materia.codigo)
                secciones_list_item_count.text =
                        String.format("%s item(es)", item.numeroSecciones)
                secciones_list_item_materia.text = item.materia.asignatura
            }
    }
}