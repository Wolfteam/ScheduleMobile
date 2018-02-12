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
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import br.com.ilhasoft.support.validation.Validator
import com.wolfteam20.schedulemobile.data.network.models.AulaDTO


/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class AulasDetailsFragment : BaseFragment(), AulasDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulasDetailsPresenter
    private lateinit var mValidator: Validator

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
        val view = inflater.inflate(R.layout.aulas_details_fragment, container, false)
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
        mValidator = Validator(binding)
        binding.root.setOnClickListener {
            mValidator.validate()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("aula_nombre", aula_nombre.text.toString())
        outState.putString("aula_capacidad", aula_capacidad.text.toString())
        super.onSaveInstanceState(outState)
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
        //TODO: No se muestra el texto al rotar
        savedInstanceState?.let {
            aula_nombre.setText(it.getString("aula_nombre"))
            aula_capacidad.setText(it.getString("aula_capacidad"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val id = baseActivity.intent.extras.getLong("ID", 0)
        //Add mode
        if (id == 0L)
            inflater?.inflate(R.menu.editardb_details_add_menu, menu)
        //Edit mode
        else
            inflater?.inflate(R.menu.editardb_details_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (!mValidator.validate() && item?.itemId != R.id.editardb_details_close)
            return true
        when (item?.itemId) {
            R.id.editardb_details_edit_delete -> mPresenter.onDeleteClicked()
            R.id.editardb_details_add_save, R.id.editardb_details_edit_save -> mPresenter.onSaveClicked()
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

    override fun prepareData(isInEditMode: Boolean) {
        //TODO Setear esta data , npi si dara error el id en la db local
        val aula = AulaDTO(
            0,
            aula_nombre.text.toString(),
            aula_capacidad.text.toString().toInt(),
            1
        )
        if (isInEditMode)
            mPresenter.updateAula(aula)
        else
            mPresenter.addAula(aula)
    }
}