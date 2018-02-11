package com.wolfteam20.schedulemobile.ui.editardb.aula

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.AulasListAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import com.wolfteam20.schedulemobile.ui.editardb.details.EditarDBDetailsActivity
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/2/2018.
 */
private const val EDITARDB_DETAILS_REQUEST_CODE = 100

class AulasFragment : BaseFragment(), AulasViewContract, EditarDBClickListenerContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulasPresenter

    private val mAdapter = AulasListAdapter(this)
    private val mActionModeCallback = ActionModeCallback()
    private var mActionMode: ActionMode? = null

    @ProvidePresenter
    fun provideHomePresenter(): AulasPresenter {
        activityComponent.inject(this)
        mPresenter.subscribe()
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editardb_fragment_common, container, false)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        editardb_fragment_common_fab.addOnMenuItemClickListener { _, _, itemId ->
            when (itemId) {
                R.id.editardb_fab_add -> mPresenter.onFABAddClicked()
                else -> mPresenter.onFABDeleteClicked()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDITARDB_DETAILS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val operation = data?.getIntExtra("OPERATION", 0)
                when (operation) {
                    0 -> {
                        val position = data.getIntExtra("POSITION", 0)
                        mAdapter.removeItem(position)
                    }
                    1 -> {
                    }
                //aca quizas solo sincronizar contra la db
                    else -> mPresenter.subscribe()
                }
            }
        }
    }

    override fun showSwipeToRefresh() {
        if (!editardb_fragment_common_swipe_to_refresh.isRefreshing)
            editardb_fragment_common_swipe_to_refresh.isRefreshing = true
    }

    override fun hideSwipeToRefresh() {
        editardb_fragment_common_swipe_to_refresh.isRefreshing = false
    }

    override fun showFAB() {
        editardb_fragment_common_fab.visibility = View.VISIBLE
    }

    override fun hideFAB() {
        editardb_fragment_common_fab.visibility = View.GONE
    }

    override fun showList(aulas: MutableList<AulaDetailsDTO>) {
        mAdapter.setItems(aulas)
    }

    override fun startDetailsActivity(itemID: Long, itemPosition: Int) {
        startActivityForResult(
            EditarDBDetailsActivity.getIntent(context!!, 1, itemID, itemPosition),
            EDITARDB_DETAILS_REQUEST_CODE
        )
    }

    override fun removeSelectedListItems() {
        mAdapter.removeItems(mAdapter.getSelectedItems())
        mActionMode?.finish()
    }

    override fun toggleItemSelection(itemPosition: Int) {
        mAdapter.toggleSelection(itemPosition)
        val count = mAdapter.getSelectedItemCount()

        if (count == 0) {
            mActionMode?.finish()
        } else {
            mActionMode?.title = count.toString()
            mActionMode?.invalidate()
        }
    }

    override fun startActionMode() {
        mActionMode = baseDrawerActivity.startSupportActionMode(mActionModeCallback)
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
            mPresenter.onItemClicked(itemID, itemPosition)
    }

    override fun onItemLongClicked(itemPosition: Int): Boolean {
        if (mActionMode == null) {
            mPresenter.onActionMode()
        }
        mPresenter.onItemLongClicked(itemPosition)
        return true
    }

    private inner class ActionModeCallback : ActionMode.Callback {
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
                    mPresenter.onFABDeleteClicked()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mAdapter.clearSelection()
            mActionMode = null
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AulasFragment::class.java)
        }
    }
}