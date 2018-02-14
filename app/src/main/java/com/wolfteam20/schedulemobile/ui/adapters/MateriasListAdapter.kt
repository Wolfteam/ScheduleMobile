package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.materias_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
class MateriasListAdapter(clickListener: ItemClickListenerContract) :
    SelectableAdapter<MateriasListAdapter.MateriasListViewHolder>() {

    private var mMateriasList: MutableList<MateriaDetailsDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriasListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val materiaView = inflater.inflate(R.layout.materias_fragment_list_item, parent, false)
        // Return a new holder instance
        return MateriasListViewHolder(materiaView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mMateriasList.size
    }

    override fun getItemId(position: Int): Long {
        return mMateriasList[position].codigo
    }

    override fun onBindViewHolder(holder: MateriasListViewHolder?, position: Int) {
        if (holder is MateriasListAdapter.MateriasListViewHolder) {
            val materia = mMateriasList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(materia)
        }
    }

    fun setItems(materias: MutableList<MateriaDetailsDTO>) {
        mMateriasList = materias
        notifyDataSetChanged()
    }


    inner class MateriasListViewHolder(root: View, clickListener: ItemClickListenerContract) :
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

        fun bind(materia: MateriaDetailsDTO) = with(itemView) {
            val nombreSemestre =
                if (materia.semestre.nombreSemestre.length > 1)
                    materia.semestre.nombreSemestre
                else
                    "Semestre: ${materia.semestre.nombreSemestre}"
            val tipo =
                if (materia.tipoMateria.nombreTipo.length > 7)
                    "Laboratorio"
                else
                    materia.tipoMateria.nombreTipo

            materia_list_item_codigo.text = String.format("Codigo: %s", materia.codigo)
            materia_list_item_nombre.text = materia.asignatura
            materia_list_item_semestre.text = nombreSemestre
            materia_list_item_tipo.text = tipo
        }
    }
}