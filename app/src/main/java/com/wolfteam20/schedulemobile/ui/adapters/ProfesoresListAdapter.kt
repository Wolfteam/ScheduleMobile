package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import kotlinx.android.synthetic.main.profesores_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class ProfesoresListAdapter(clickListener: EditarDBClickListenerContract) :
    SelectableAdapter<ProfesoresListAdapter.ProfesoresListViewHolder>() {

    private var mProfesorList: MutableList<ProfesorDetailsDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesoresListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val profesorView = inflater.inflate(R.layout.profesores_fragment_list_item, parent, false)
        // Return a new holder instance
        return ProfesoresListAdapter.ProfesoresListViewHolder(profesorView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mProfesorList.size
    }

    override fun getItemId(position: Int): Long {
        return mProfesorList[position].cedula
    }

    override fun onBindViewHolder(holder: ProfesoresListViewHolder?, position: Int) {
        if (holder is ProfesoresListAdapter.ProfesoresListViewHolder) {
            val periodo = mProfesorList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(periodo)
        }
    }

    fun setItems(profesores: MutableList<ProfesorDetailsDTO>) {
        mProfesorList = profesores
        notifyDataSetChanged()
    }


    class ProfesoresListViewHolder(root: View, clickListener: EditarDBClickListenerContract) :
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

        fun bind(profesor: ProfesorDetailsDTO) = with(itemView) {
            profesor_list_item_cedula.text = String.format("Cedula: %s", profesor.cedula)
            profesor_list_item_fullname.text =
                    String.format("%s %s", profesor.nombre, profesor.apellido)
            profesor_list_item_horas_a_cumplir.text =
                    String.format("Horas a cumplir: %s", profesor.prioridad?.horasACumplir)
            profesor_list_item_prioridad.text =
                    String.format("Prioridad: %s", profesor.prioridad?.codigoPrioridad)
        }
    }
}