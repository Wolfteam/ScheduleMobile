package com.wolfteam20.schedulemobile.ui.editardb.aula

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class AulasFragment : BaseFragment(), AulasViewContract, EditarDBClickListenerContract {
    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulasPresenter

    private val mAdapter = AulasListAdapter(this)


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
        editardb_fragment_common_fab.addOnMenuItemClickListener { miniFab, label, itemId ->
            when (itemId) {
                R.id.editardb_fab_add -> mPresenter.onFABAddClicked()
                else -> mPresenter.onFABDeleteClicked(mAdapter.getItems(mAdapter.getSelectedItems()))
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

    override fun startDetailsActivity(id: Long) {
        startActivity(EditarDBDetailsActivity.getIntent(context!!, 1, id))
    }

    override fun removeSelectedListItems() {
        mAdapter.removeItems(mAdapter.getSelectedItems())
    }

    override fun toggleItemSelection(position: Int) {
        mAdapter.toggleSelection(position)
    }

    override fun onItemClicked(id: Long) {
        mPresenter.onItemClicked(id)
    }

    override fun onItemLongClicked(position: Int): Boolean {
        mPresenter.onItemLongClicked(position)
        return true
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AulasFragment::class.java)
        }
    }
}