package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
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
    SelectableAdapter<SeccionesListAdapter.SeccionesListViewHolder>() {

    private var mSeccionList: MutableList<SeccionDetailsDTO> = mutableListOf()
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

    override fun getItemCount(): Int {
        return mSeccionList.size
    }

    override fun getItemId(position: Int): Long {
        return mSeccionList[position].materia.codigo
    }

    override fun onBindViewHolder(
        holder: SeccionesListAdapter.SeccionesListViewHolder?,
        position: Int
    ) {
        if (holder is SeccionesListAdapter.SeccionesListViewHolder) {
            val seccion = mSeccionList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(seccion)
        }
    }

    fun setItems(secciones: MutableList<SeccionDetailsDTO>) {
        mSeccionList = secciones
        notifyDataSetChanged()
    }


    inner class SeccionesListViewHolder(root: View, clickListener: ItemClickListenerContract) :
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

        fun bind(seccion: SeccionDetailsDTO) = with(itemView) {
            secciones_list_item_alumnos.text =
                    String.format("%s alumno(s) c/u", seccion.cantidadAlumnos)
            secciones_list_item_codigo.text = String.format("Codigo: %s", seccion.materia.codigo)
            secciones_list_item_count.text =
                    String.format("%s seccion(es)", seccion.numeroSecciones)
            secciones_list_item_materia.text = seccion.materia.asignatura
        }
    }
}