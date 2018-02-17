package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_fragment_periodos_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class PeriodoListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<PeriodoAcademicoDTO>() {

    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodosListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val periodoView = inflater.inflate(R.layout.editardb_fragment_periodos_list_item, parent, false)
        // Return a new holder instance
        return PeriodosListViewHolder(periodoView, mClickListener)
    }

    override fun getItemId(position: Int): Long {
        return mItemList[position].idPeriodo
    }

    inner class PeriodosListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: PeriodoAcademicoDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                periodo_list_item_fecha_creacion.text =
                        String.format("Creado: %s", sdf.format(item.fechaCreacion))
                periodo_list_item_nombre.text = item.nombrePeriodo
                periodo_list_item_status.text =
                        if (item.status) "Status: Activo" else "Status: Inactivo"
            }
    }
}