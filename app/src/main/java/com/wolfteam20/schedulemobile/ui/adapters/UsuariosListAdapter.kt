package com.wolfteam20.schedulemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.usuarios_fragment_list_item.view.*

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */
class UsuariosListAdapter(clickListener: ItemClickListenerContract) :
    BaseItemListAdapter<UsuarioDetailsDTO>() {

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

    override fun getItemId(position: Int): Long {
        return mItemList[position].cedula
    }

    inner class UsuariosListViewHolder(root: View, clickListener: ItemClickListenerContract) :
        ItemViewHolder(root, clickListener) {

        override fun bind(item: UsuarioDetailsDTO, itemPosition: Int, isItemSelected: Boolean) =
            with(itemView) {
                usuarios_list_item_cedula.text = String.format("Cedula: %s", item.cedula)
                usuarios_list_item_privilegio.text = item.privilegios.nombrePrivilegio
                usuarios_list_item_username.text = item.username
            }
    }

}