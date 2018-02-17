package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.adapters.BaseItemListAdapterContract
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.ActionModeCallback
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBDetailsActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.editardb_fragment_common.*

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */


abstract class ItemBaseFragment<TItem>: BaseFragment(), ItemSpecficViewContract<TItem> {
    protected val EDITARDB_DETAILS_REQUEST_CODE = 100
    protected val DELETE_OPERATION = 0
    protected val CANCEL_OPERATION = 1
    protected val ADD_OPERATION = 2
    protected val UPDATE_OPERATION = 3
    protected var mActionMode: ActionMode? = null

    protected lateinit var mAdapter: BaseItemListAdapterContract<TItem>
    protected lateinit var mActionModeCallback: ActionModeCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editardb_fragment_common, container, false)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        val llm = LinearLayoutManager(context)
        editardb_fragment_common_recycler_view.layoutManager = llm
        editardb_fragment_common_recycler_view.addItemDecoration(
            DividerItemDecoration(
                editardb_fragment_common_recycler_view.context,
                llm.orientation
            )
        )
        editardb_fragment_common_recycler_view.itemAnimator = DefaultItemAnimator()
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

    override fun startDetailsActivity(
        fragment: Int,
        itemID: Long,
        itemPosition: Int,
        item: Parcelable?
    ) {
        startActivityForResult(
            EditarDBDetailsActivity.getIntent(context!!, fragment, itemID, itemPosition)
                .putExtra("ITEM", item),
            EDITARDB_DETAILS_REQUEST_CODE
        )
    }


    override fun showList(items: MutableList<TItem>) {
        mAdapter.setItems(items)
    }

    override fun addItem(item: TItem) {
        mAdapter.addItem(item)
    }

    override fun updateItem(position: Int, item: TItem) {
        mAdapter.updateItem(position, item)
    }

    override fun removeSelectedListItems() {
        mAdapter.removeItems(mAdapter.getSelectedItems())
    }

    override fun startActionMode() {
        mActionMode = baseDrawerActivity.startSupportActionMode(mActionModeCallback)
    }

    override fun stopActionMode() {
        mAdapter.clearSelection()
        mActionMode?.finish()
        mActionMode = null
    }

    override fun setActionModeTitle(title: String) {
        mActionMode?.title = title
        mActionMode?.invalidate()
    }

    override fun showNoItemsSelected() {
        Toasty.info(context!!, resources.getString(R.string.no_items_selected)).show()
    }
}