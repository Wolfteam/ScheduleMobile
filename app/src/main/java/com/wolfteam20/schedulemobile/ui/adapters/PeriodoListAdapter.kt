package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import kotlinx.android.synthetic.main.periodo_fragment_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class PeriodoListAdapter(clickListener: EditarDBClickListenerContract) :
    SelectableAdapter<PeriodoListAdapter.PeriodosListViewHolder>() {

    private var mPeriodoList: MutableList<PeriodoAcademicoDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodosListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val periodoView = inflater.inflate(R.layout.periodo_fragment_list_item, parent, false)
        // Return a new holder instance
        return PeriodoListAdapter.PeriodosListViewHolder(periodoView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mPeriodoList.size
    }

    override fun getItemId(position: Int): Long {
        return mPeriodoList[position].idPeriodo
    }

    override fun onBindViewHolder(holder: PeriodosListViewHolder?, position: Int) {
        if (holder is PeriodoListAdapter.PeriodosListViewHolder) {
            val periodo = mPeriodoList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(periodo)
        }
    }

    fun setItems(periodos: MutableList<PeriodoAcademicoDTO>) {
        mPeriodoList = periodos
        notifyDataSetChanged()
    }


    class PeriodosListViewHolder(root: View, clickListener: EditarDBClickListenerContract) :
        RecyclerView.ViewHolder(root) {

        private val mClickListener = clickListener

        init {
            root.setOnClickListener {
                mClickListener.onItemClicked(itemId)
            }
            root.setOnLongClickListener {
                mClickListener.onItemLongClicked(layoutPosition)
                return@setOnLongClickListener false
            }
        }

        fun bind(periodo: PeriodoAcademicoDTO) = with(itemView) {
            val sdf =   SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            periodo_list_item_fecha_creacion.text =
                    String.format("Creado: %s", sdf.format(periodo.fechaCreacion))
            periodo_list_item_nombre.text = periodo.nombrePeriodo
            periodo_list_item_status.text =
                    if (periodo.status) "Status: Activo" else "Status: Inactivo"
        }
    }

}