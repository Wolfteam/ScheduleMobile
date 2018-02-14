package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBDetailsActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.editardb_fragment_common.*

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */

private const val EDITARDB_DETAILS_REQUEST_CODE = 100
private const val DELETE_OPERATION = 0
private const val CANCEL_OPERATION = 1
private const val ADD_OPERATION = 2
private const val UPDATE_OPERATION = 3

abstract class ItemBaseFragment : BaseFragment(),
    ItemBaseViewContract {

    protected var mActionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editardb_fragment_common, container, false)
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

    override fun setActionModeTitle(title: String) {
        mActionMode?.title = title
        mActionMode?.invalidate()
    }

    override fun showNoItemsSelected() {
        Toasty.info(context!!, resources.getString(R.string.no_items_selected)).show()
    }
}