package com.wolfteam20.schedulemobile.ui.editardb.aula

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.AulasListAdapter
import com.wolfteam20.schedulemobile.ui.editardb.ActionModeCallback
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/2/2018.
 */
private const val EDITARDB_DETAILS_REQUEST_CODE = 100
private const val DELETE_OPERATION = 0
private const val CANCEL_OPERATION = 1
private const val ADD_OPERATION = 2
private const val UPDATE_OPERATION = 3

class AulasFragment : ItemBaseFragment(), AulasViewContract,
    ItemClickListenerContract {
    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulasPresenter

    private val mAdapter = AulasListAdapter(this)
    private lateinit var mActionModeCallback: ActionModeCallback<AulasViewContract>

    @ProvidePresenter
    fun provideHomePresenter(): AulasPresenter {
        activityComponent.inject(this)
        mPresenter.subscribe()
        return mPresenter
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        editardb_fragment_common_fab.addOnMenuItemClickListener { _, _, itemId ->
            when (itemId) {
                R.id.editardb_fab_add -> mPresenter.onFABAddClicked()
                else -> mPresenter.onFABDeleteClicked(mAdapter.getSelectedItemCount())
            }
        }
        val llm = LinearLayoutManager(context)
        editardb_fragment_common_recycler_view.layoutManager = llm
        editardb_fragment_common_recycler_view.addItemDecoration(
            DividerItemDecoration(
                editardb_fragment_common_recycler_view.context,
                llm.orientation
            )
        )
        editardb_fragment_common_recycler_view.itemAnimator = DefaultItemAnimator()
        editardb_fragment_common_recycler_view.adapter = mAdapter
        editardb_fragment_common_swipe_to_refresh.setOnRefreshListener { mPresenter.subscribe() }

        mActionModeCallback = ActionModeCallback(mPresenter, mAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDITARDB_DETAILS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val operation = data?.getIntExtra("OPERATION", 0)
                val position = data?.getIntExtra("POSITION", 0)
                val item = data?.getParcelableExtra<AulaDetailsDTO>("ITEM")
                when (operation) {
                    DELETE_OPERATION -> {
                        mAdapter.removeItem(position!!)
                    }
                    CANCEL_OPERATION -> {
                    }
                    UPDATE_OPERATION -> mPresenter.onItemUpdated(item!!, position!!)
                    else -> mPresenter.onItemAdded(0)
                }
            }
        }
    }

    override fun showList(aulas: MutableList<AulaDetailsDTO>) {
        mAdapter.setItems(aulas)
    }

    override fun addItem(aula: AulaDetailsDTO) {
        mAdapter.addItem(aula)
    }

    override fun updateItem(position: Int, aula: AulaDetailsDTO) {
        mAdapter.updateItem(position, aula)
    }

    override fun removeSelectedListItems() {
        mAdapter.removeItems(mAdapter.getSelectedItems())
    }

    override fun toggleItemSelection(itemPosition: Int) {
        mAdapter.toggleSelection(itemPosition)
        mPresenter.onToggleItemSelection(mAdapter.getSelectedItemCount())
    }

    override fun startActionMode() {
        mActionMode = baseDrawerActivity.startSupportActionMode(mActionModeCallback)
    }

    override fun stopActionMode() {
        mAdapter.clearSelection()
        mActionMode?.finish()
        mActionMode = null
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

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AulasFragment::class.java)
        }
    }
}