package com.wolfteam20.schedulemobile.ui.editardb.periodos

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.adapters.PeriodoListAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
class PeriodosFragment : ItemBaseFragment(), PeriodosViewContract,
    ItemClickListenerContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: PeriodosPresenter

    private val mAdapter = PeriodoListAdapter(this)

    @ProvidePresenter
    fun providePeriodoPresenter(): PeriodosPresenter {
        activityComponent.inject(this)
        mPresenter.subscribe()
        return mPresenter
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

    override fun onItemClicked(itemID: Long, itemPosition: Int) {
        Toasty.warning(context!!, "Not implemented").show()
    }

    override fun onItemLongClicked(itemPosition: Int): Boolean {
        return true
    }

    override fun showList(periodos: MutableList<PeriodoAcademicoDTO>) {
        mAdapter.setItems(periodos)
    }

    override fun toggleItemSelection(itemPosition: Int) {
        mAdapter.toggleSelection(itemPosition)
    }

    override fun removeSelectedListItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startActionMode() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopActionMode() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showConfirmDelete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}