package com.wolfteam20.schedulemobile.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import kotlinx.android.synthetic.main.usuarios_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
class UsuariosListAdapter(clickListener: EditarDBClickListenerContract) :
    SelectableAdapter<UsuariosListAdapter.UsuariosListViewHolder>() {

    private var mUsuariosList: MutableList<UsuarioDetailsDTO> = mutableListOf()
    private val mClickListener = clickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsuariosListAdapter.UsuariosListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val usuarioView = inflater.inflate(R.layout.usuarios_fragment_list_item, parent, false)
        // Return a new holder instance
        return UsuariosListViewHolder(usuarioView, mClickListener)
    }

    override fun getItemCount(): Int {
        return mUsuariosList.size
    }

    override fun getItemId(position: Int): Long {
        return mUsuariosList[position].cedula
    }

    override fun onBindViewHolder(
        holder: UsuariosListAdapter.UsuariosListViewHolder?,
        position: Int
    ) {
        if (holder is UsuariosListAdapter.UsuariosListViewHolder) {
            val usuario = mUsuariosList[position]
            //val isItemSelected = isSelected(position)
            holder.bind(usuario)
        }
    }

    fun setItems(usuario: MutableList<UsuarioDetailsDTO>) {
        mUsuariosList = usuario
        notifyDataSetChanged()
    }


    inner class UsuariosListViewHolder(root: View, clickListener: EditarDBClickListenerContract) :
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

        fun bind(usuario: UsuarioDetailsDTO) = with(itemView) {
            usuarios_list_item_cedula.text = String.format("Cedula: %s", usuario.cedula)
            usuarios_list_item_privilegio.text = usuario.privilegios.nombrePrivilegio
            usuarios_list_item_username.text = usuario.username
        }
    }

}