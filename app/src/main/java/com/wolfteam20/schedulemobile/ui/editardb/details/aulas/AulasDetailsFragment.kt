package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BaseActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.aulas_details_fragment.*
import kotlinx.android.synthetic.main.editardb_details_toolbar.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class AulasDetailsFragment : BaseFragment(), AulasDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulasDetailsPresenter

    @ProvidePresenter
    fun provideHomePresenter(): AulasDetailsPresenter {
        activityComponent.inject(this)
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.aulas_details_fragment, container, false)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val id = baseActivity.intent.extras.getLong("ID", 0)
        val position = baseActivity.intent.extras.getInt("POSITION", 0)

        val appCompatActivity = baseActivity as AppCompatActivity
        appCompatActivity.setSupportActionBar(editardb_details_fragment_toolbar)
        if (id != 0L)
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.edit)
        else
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.add)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        mPresenter.subscribe(id, position)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.editardb_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.editardb_details_delete -> mPresenter.onDeleteClicked()
            R.id.editardb_details_save -> mPresenter.onSaveClicked()
            else -> mPresenter.onCancelClicked()
        }
        return true
    }

    override fun showLoading() {
        aulas_details_progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        aulas_details_progress_bar.visibility = View.GONE
    }

    override fun showItem(aula: AulaDetailsDTO) {
        aula_nombre.setText(aula.nombreAula)
        aula_capacidad.setText(aula.capacidad.toString())
    }

    override fun showConfirmDeleteDialog() {
        val dialog: AlertDialog = AlertDialog.Builder(baseActivity)
            .setTitle(resources.getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes), { _, _ ->
                mPresenter.deleteAula()
            })
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }

    override fun finishActivity(operation: Int, position: Int) {
        baseActivity.intent.putExtra("OPERATION", operation)
        baseActivity.intent.putExtra("POSITION", position)
        baseActivity.setResult(BaseActivity.RESULT_OK, baseActivity.intent)
        baseActivity.onBackPressed()
    }

}