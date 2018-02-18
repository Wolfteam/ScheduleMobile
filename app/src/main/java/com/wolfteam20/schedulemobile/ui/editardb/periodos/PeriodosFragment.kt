package com.wolfteam20.schedulemobile.ui.editardb.periodos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.adapters.PeriodoListAdapter
import com.wolfteam20.schedulemobile.ui.editardb.ActionModeCallback
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class PeriodosFragment : ItemBaseFragment<PeriodoAcademicoDTO>(), PeriodosViewContract,
    ItemClickListenerContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: PeriodosPresenter

    @ProvidePresenter
    fun providePeriodoPresenter(): PeriodosPresenter {
        activityComponent.inject(this)
        mPresenter.subscribe()
        return mPresenter
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        super.initLayout(view, savedInstanceState)
        editardb_fragment_common_fab.addOnMenuItemClickListener { _, _, itemId ->
            when (itemId) {
                R.id.editardb_fab_add -> mPresenter.onFABAddClicked()
                else -> mPresenter.onFABDeleteClicked(mAdapter.getSelectedItemCount())
            }
        }

        mAdapter = PeriodoListAdapter(this)
        editardb_fragment_common_recycler_view.adapter = mAdapter as PeriodoListAdapter

        editardb_fragment_common_swipe_to_refresh.setOnRefreshListener { mPresenter.subscribe() }

        mActionModeCallback = ActionModeCallback(mPresenter, mAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDITARDB_DETAILS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val operation = data?.getIntExtra("OPERATION", 0)
                val position = data?.getIntExtra("POSITION", 0)
                val item = data?.getParcelableExtra<PeriodoAcademicoDTO>("ITEM")
                when (operation) {
                    DELETE_OPERATION -> mPresenter.onItemRemoved(position!!)
                    CANCEL_OPERATION -> {
                    }
                    UPDATE_OPERATION -> mPresenter.onItemUpdated(item!!, position!!)
                    else -> mPresenter.onItemAdded()
                }
            }
        }
    }

    override fun onItemClicked(itemID: Long, itemPosition: Int) {
        if (mActionMode != null)
            mPresenter.onItemLongClicked(itemPosition)
        else
            mPresenter.onItemClicked(itemID, itemPosition, mAdapter.getItem(itemPosition))
    }

    override fun onItemLongClicked(itemPosition: Int): Boolean {
        if (mActionMode == null) {
            mPresenter.onActionMode()
        }
        mPresenter.onItemLongClicked(itemPosition)
        return true
    }

    override fun toggleItemSelection(itemPosition: Int) {
        mAdapter.toggleSelection(itemPosition)
        mPresenter.onToggleItemSelection(mAdapter.getSelectedItemCount())
    }

    override fun showConfirmDelete() {
        val dialog: AlertDialog = AlertDialog.Builder(baseDrawerActivity)
            .setTitle(resources.getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes), { _, _ ->
                mPresenter.deleteItems(mAdapter.getItems(mAdapter.getSelectedItems()))
            })
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }
}