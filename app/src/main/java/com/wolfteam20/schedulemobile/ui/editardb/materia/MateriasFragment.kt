package com.wolfteam20.schedulemobile.ui.editardb.materia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.MateriasListAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemClickListenerContract
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.editardb_fragment_common.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/2/2018.
 */
class MateriasFragment : ItemBaseFragment(), MateriasViewContract,
    ItemClickListenerContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter : MateriasPresenter

    private val mAdapter =  MateriasListAdapter(this)

    @ProvidePresenter
    fun provideAulasPresenter() : MateriasPresenter{
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

    override fun showList(materias: MutableList<MateriaDetailsDTO>) {
        mAdapter.setItems(materias)
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

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MateriasFragment::class.java)
        }
    }
}