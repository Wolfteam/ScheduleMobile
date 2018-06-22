package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_common_item_selected_overlay.view.*
import kotlinx.android.synthetic.main.editardb_fragment_prof_mat_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
class ProfesoresMateriasListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<ProfesorMateriaDetailsDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfesoresMateriasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val profesorView =
            inflater.inflate(R.layout.editardb_fragment_prof_mat_list_item, parent, false)
        // Return a new holder instance
        return ProfesoresMateriasListViewHolder(profesorView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].id
    }

    inner class ProfesoresMateriasListViewHolder(
        root: View,
        clickListener: ItemClickListenerContract
    ) : ItemViewHolder(root, clickListener) {

        override fun bind(
            item: ProfesorMateriaDetailsDTO,
            itemPosition: Int,
            isItemSelected: Boolean
        ) = with(itemView) {
            pm_list_item_cedula.text = String.format("Cedula: %s", item.profesor.cedula)
            pm_list_item_codigo.text = String.format("Codigo: %s", item.materia.codigo)
            pm_list_item_fullname.text =
                    String.format("%s %s", item.profesor.nombre, item.profesor.apellido)
            pm_list_item_materia.text = item.materia.asignatura
            editardb_common_item_selected_overlay.visibility =
                    if (isItemSelected) View.VISIBLE else View.INVISIBLE
        }
    }
}