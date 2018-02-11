package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria

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
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresMateriasListAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBClickListenerContract
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class ProfesorMateriaFragment : BaseFragment(), ProfesorMateriaViewContract,
    EditarDBClickListenerContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: ProfesorMateriaPresenter

    private val mAdapter = ProfesoresMateriasListAdapter(this)

    @ProvidePresenter
    fun provideProfesorMateriaPresenter(): ProfesorMateriaPresenter {
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
    }

    override fun onItemClicked(itemID: Long , itemPosition: Int) {
        Toasty.warning(context!!, "Not implemented").show()
    }

    override fun onItemLongClicked(itemPosition: Int): Boolean {
        return true
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

    override fun showList(pm: MutableList<ProfesorMateriaDetailsDTO>) {
        mAdapter.setItems(pm)
    }

    override fun startDetailsActivity(itemID: Long , itemPosition: Int) {
        Toasty.warning(context!!, "Not implemented").show()
    }

    override fun toggleItemSelection(itemPosition: Int) {
        mAdapter.toggleSelection(itemPosition)
    }

}