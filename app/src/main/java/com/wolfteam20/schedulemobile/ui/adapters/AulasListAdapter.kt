package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import kotlinx.android.synthetic.main.aulas_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
class AulasListAdapter(clickListener: EditarDBClickListenerContract) :
    SelectableAdapter<AulasListAdapter.AulasListViewHolder>() {

    private var mAulasList: MutableList<AulaDetailsDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val aulaView = inflater.inflate(R.layout.aulas_fragment_list_item, parent, false)
        // Return a new holder instance
        return AulasListAdapter.AulasListViewHolder(aulaView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mAulasList.size
    }

    override fun getItemId(position: Int): Long {
        return mAulasList[position].idAula
    }

    override fun onBindViewHolder(holder: AulasListViewHolder?, position: Int) {
        if (holder is AulasListViewHolder) {
            val aula = mAulasList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(aula)
        }
    }

    fun setItems(aulas: MutableList<AulaDetailsDTO>) {
        mAulasList = aulas
        notifyDataSetChanged()
    }

    class AulasListViewHolder(root: View, clickListener: EditarDBClickListenerContract) :
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

        fun bind(aula: AulaDetailsDTO) = with(itemView) {
            val nombreAula =
                if (aula.nombreAula.length > 5) aula.nombreAula else "Aula: ${aula.nombreAula}"
            val tipo =
                if (aula.tipoAula.nombreTipo.length > 7) "Laboratorio" else aula.tipoAula.nombreTipo

            aula_list_item_nombre.text = nombreAula
            aula_list_item_capacidad.text = String.format("Capacidad: %d", aula.capacidad)
            aula_list_item_tipo.text = tipo

        }
    }
}