package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import kotlinx.android.synthetic.main.profesorxmateria_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
class ProfesoresMateriasListAdapter(clickListener: EditarDBClickListenerContract) :
    SelectableAdapter<ProfesoresMateriasListAdapter.ProfesoresMateriasListViewHolder>() {

    private var mPMList: MutableList<ProfesorMateriaDetailsDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfesoresMateriasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val profesorView =
            inflater.inflate(R.layout.profesorxmateria_fragment_list_item, parent, false)
        // Return a new holder instance
        return ProfesoresMateriasListViewHolder(profesorView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mPMList.size
    }

    override fun getItemId(position: Int): Long {
        return mPMList[position].id
    }

    override fun onBindViewHolder(holder: ProfesoresMateriasListViewHolder?, position: Int) {
        if (holder is ProfesoresMateriasListAdapter.ProfesoresMateriasListViewHolder) {
            val pm = mPMList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(pm)
        }
    }

    fun setItems(relaciones: MutableList<ProfesorMateriaDetailsDTO>) {
        mPMList = relaciones
        notifyDataSetChanged()
    }

    inner class ProfesoresMateriasListViewHolder(
        root: View,
        clickListener: EditarDBClickListenerContract
    ) :
        RecyclerView.ViewHolder(root) {

        private val mClickListener = clickListener

        init {
            root.setOnClickListener {
                mClickListener.onItemClicked(getItemId(layoutPosition), layoutPosition)
            }
            root.setOnLongClickListener {
                return@setOnLongClickListener mClickListener.onItemLongClicked(layoutPosition)
            }
        }

        fun bind(relacion: ProfesorMateriaDetailsDTO) = with(itemView) {
            pm_list_item_cedula.text = String.format("Cedula: %s", relacion.profesor.cedula)
            pm_list_item_codigo.text = String.format("Codigo: %s", relacion.materia.codigo)
            pm_list_item_fullname.text =
                    String.format("%s %s", relacion.profesor.nombre, relacion.profesor.apellido)
            pm_list_item_materia.text = relacion.materia.asignatura
        }
    }
}