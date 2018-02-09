package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
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
            val isItemSelected = isSelected(position)
            holder.bind(aula, isItemSelected)
        }
    }

    /**
     * Obtiene una lista de disponibilidades en las posiciones indicadas
     * por [itemsPosition]
     */
    fun getItems(itemsPosition: ArrayList<Int>): MutableList<AulaDetailsDTO> {
        return itemsPosition.map { mAulasList[it] }.toMutableList()
    }

    fun setItems(aulas: MutableList<AulaDetailsDTO>) {
        mAulasList = aulas
        notifyDataSetChanged()
    }

    /**
     * Remueve un item de la lista en la [position] indicada y
     * notifica  de los cambios al adapter
     */
    private fun removeItem(position: Int) {
        mAulasList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Remueve una o varias aulas en las [positions] indicadas
     * y limpia los elementos seleccionados
     */
    fun removeItems(positions: ArrayList<Int>) {
        //Esto para evitar que se queden items seleccionados
        clearSelection()
        positions.sortDescending()
        while (!positions.isEmpty()) {
            if (positions.size == 1) {
                removeItem(positions[0])
                positions.removeAt(0)
            } else {
                var count = 1
                //Este while sirve para casos donde seleccionas multiples items posiciones seguidas
                //ee.g(5,4,3)
                while (positions.size > count && positions[count] == positions[count - 1] - 1) {
                    ++count
                }
                if (count == 1)
                    removeItem(positions[0])
                else
                    removeRange(positions[count - 1], count)

                for (i in 0 until count)
                    positions.removeAt(0)
            }
        }
    }

    /**
     * Remueve un rango de aulas partiendo de  [positionStart] hasta [itemCount]
     * y notifica de los cambios al adapter
     */
    private fun removeRange(positionStart: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            mAulasList.removeAt(positionStart)
        }
        notifyItemRangeRemoved(positionStart, itemCount)
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

        fun bind(aula: AulaDetailsDTO, isItemSelected: Boolean) = with(itemView) {
            val nombreAula =
                if (aula.nombreAula.length > 5) aula.nombreAula else "Aula: ${aula.nombreAula}"
            val tipo =
                if (aula.tipoAula.nombreTipo.length > 7) "Laboratorio" else aula.tipoAula.nombreTipo

            aula_list_item_nombre.text = nombreAula
            aula_list_item_capacidad.text = String.format("Capacidad: %d", aula.capacidad)
            aula_list_item_tipo.text = tipo
            aulas_list_item_selected_overlay.visibility =
                    if (isItemSelected) View.VISIBLE else View.INVISIBLE
        }
    }
}