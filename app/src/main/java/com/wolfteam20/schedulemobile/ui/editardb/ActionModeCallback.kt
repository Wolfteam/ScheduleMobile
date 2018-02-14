package com.wolfteam20.schedulemobile.ui.editardb

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.adapters.SelectableAdapterContract
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseViewContract

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */
class ActionModeCallback<T : ItemBaseViewContract>(
    presenter: ItemBasePresenter<T>,
    adapter: SelectableAdapterContract
) :
    ActionMode.Callback {

    private val mPresenter = presenter
    private val mAdapter = adapter

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                mPresenter.onFABDeleteClicked(mAdapter.getSelectedItemCount())
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        mPresenter.onToggleItemSelection(0)
    }
}