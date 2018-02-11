package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsPresenter
import kotlinx.android.synthetic.main.disponibilidad_details_list_item.view.*


/**
 * Created by Efrain.Bastidas on 1/15/2018.
 */
class DispDetailsListAdapter(
    presenter: DispDetailsPresenter,
    horas: Array<String>,
    clickListener: DispDetailsListViewHolder.ClickListener
) : SelectableAdapter<DispDetailsListAdapter.DispDetailsListViewHolder>() {

    private val mClickListener: DispDetailsListViewHolder.ClickListener = clickListener
    private val mPresenter: DispDetailsPresenter = presenter
    private var mDisponibilidadList: MutableList<DisponibilidadDTO> = mutableListOf()
    private var mHoras = horas

    class DispDetailsListViewHolder(root: View, clickListener: ClickListener) :
        RecyclerView.ViewHolder(root) {

        private val mClickListener: ClickListener = clickListener

        interface ClickListener {
            fun onItemClicked(position: Int)
            fun onItemLongClicked(position: Int): Boolean
        }

        init {
            root.setOnClickListener {
                mClickListener.onItemClicked(layoutPosition)
            }
            root.setOnLongClickListener {
                return@setOnLongClickListener mClickListener.onItemLongClicked(layoutPosition)
            }
        }

        fun bind(text: String, isSelected: Boolean, position: Int) = with(itemView) {
            disp_details_from_to_hours.text = text
            disp_details_selected_overlay.visibility =
                    if (isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mDisponibilidadList.size
    }

    override fun onBindViewHolder(holder: DispDetailsListViewHolder?, position: Int) {
        if (holder is DispDetailsListViewHolder) {
            val disponibilidad = mDisponibilidadList[position]
            val isItemSelected = isSelected(position)
            holder.bind(
                getTextToShow(disponibilidad.idHoraInicio, disponibilidad.idHoraFin),
                isItemSelected,
                position
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispDetailsListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val dispDetailsView =
            inflater.inflate(R.layout.disponibilidad_details_list_item, parent, false)
        // Return a new holder instance
        return DispDetailsListViewHolder(dispDetailsView, mClickListener)
    }

    /**
     * Agrega una lista de [disponibilidades] al adapter y los muestra
     */
    fun addItems(disponibilidades: List<DisponibilidadDTO>) {
        mDisponibilidadList.clear()
        mDisponibilidadList.addAll(disponibilidades)
        notifyDataSetChanged()
    }

    /**
     * Agrega una [disponibilidad] al adapter y lo muestra
     */
    fun addItem(disponibilidad: DisponibilidadDTO) {
        mDisponibilidadList.add(disponibilidad)
        notifyItemInserted(mDisponibilidadList.size - 1)
    }

    /**
     * Obtiene la lista de disponibilidades del adapter
     * @return MutableList<DisponibilidadDTO>
     */
    fun getAllItems(): MutableList<DisponibilidadDTO> {
        return mDisponibilidadList
    }

    /**
     * Obtiene una lista de disponibilidades en las posiciones indicadas
     * por [itemsPosition]
     */
    fun getItems(itemsPosition: ArrayList<Int>): List<DisponibilidadDTO> {
        return itemsPosition.map { mDisponibilidadList[it] }
    }

    /**
     * Genera el texto a mostrar en la view item
     * @return Texto a mostrar
     */
    private fun getTextToShow(idHoraInicio: Int, idHoraFin: Int): String {
        return "${mHoras[idHoraInicio - 1]} a ${mHoras[idHoraFin - 1]}"
    }

    /**
     * Remueve un item de la lista en la [position] indicada y
     * notifica  de los cambios al adapter
     */
    private fun removeItem(position: Int) {
        mDisponibilidadList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Remueve uno o varias disponibilidades en las [positions] indicadas
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
        mPresenter.updateItems(mDisponibilidadList)
    }

    /**
     * Remueve un rango de disponbilidades partiendo de  [positionStart] hasta [itemCount]
     * y notifica de los cambios al adapter
     */
    private fun removeRange(positionStart: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            mDisponibilidadList.removeAt(positionStart)
        }
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    /**
     * Setea por primera vez los items en el adapter
     */
    fun setItems(disponibilidades: MutableList<DisponibilidadDTO>) {
        mDisponibilidadList = disponibilidades
        notifyDataSetChanged()
    }
}
