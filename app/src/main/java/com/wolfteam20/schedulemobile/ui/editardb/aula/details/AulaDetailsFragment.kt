package com.wolfteam20.schedulemobile.ui.editardb.aula.details

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import br.com.ilhasoft.support.validation.Validator
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO
import com.wolfteam20.schedulemobile.ui.adapters.TipoAulaMateriaSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_aulas.*
import javax.inject.Inject


/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class AulaDetailsFragment : ItemDetailsBaseFragment(), AulaDetailsViewContract {
    @Inject
    @InjectPresenter
    lateinit var mPresenter: AulaDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mAdapter: TipoAulaMateriaSpinnerAdapter

    @ProvidePresenter
    fun provideAulaDetailsPresenter(): AulaDetailsPresenter {
        activityComponent.inject(this)
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_aulas, container, false)
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
        mValidator = Validator(binding)
        binding.root.setOnClickListener {
            mValidator.validate()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("aula_nombre", aula_details_nombre.text.toString())
        outState.putString("aula_capacidad", aula_details_capacidad.text.toString())
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

        mAdapter = TipoAulaMateriaSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )
        aula_details_tipo_dropdown.adapter = mAdapter

        val model = baseActivity.intent.extras.getParcelable<AulaDetailsDTO>("ITEM")
        mPresenter.subscribe(id, position, model)

        //TODO: No se muestra el texto al rotar
        savedInstanceState?.let {
            aula_details_nombre.setText(it.getString("aula_nombre"))
            aula_details_capacidad.setText(it.getString("aula_capacidad"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.editardb_details_edit_delete -> mPresenter.onDeleteClicked()
            R.id.editardb_details_add_save, R.id.editardb_details_edit_save -> {
                if (mValidator.validate())
                    mPresenter.onSaveClicked()
            }
            else -> mPresenter.onCancelClicked()
        }
        return true
    }

    override fun enableAllViews(enabled: Boolean) {
        aula_details_nombre.isEnabled = enabled
        aula_details_capacidad.isEnabled = enabled
        aula_details_tipo_dropdown.isEnabled = enabled
    }

    override fun showItem(aula: AulaDetailsDTO) {
        aula_details_nombre.setText(aula.nombreAula)
        aula_details_capacidad.setText(aula.capacidad.toString())
        val itemPosition = mAdapter.getPosition(aula.tipoAula.idTipo)
        aula_details_tipo_dropdown.setSelection(itemPosition)
    }

    override fun setTipoAulaSpinnerItems(tipos: MutableList<TipoAulaMateriaDTO>) {
        mAdapter.setItems(tipos)
    }

    override fun showConfirmDeleteDialog() {
        val dialog: AlertDialog = AlertDialog.Builder(baseActivity)
            .setTitle(resources.getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes), { _, _ ->
                mPresenter.delete()
            })
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }

    override fun prepareData(isInEditMode: Boolean) {
        val aula = AulaDetailsDTO(
            0,
            aula_details_nombre.text.toString(),
            aula_details_capacidad.text.toString().toInt(),
            mAdapter.getItem(aula_details_tipo_dropdown.selectedItemId)
        )
        if (isInEditMode)
            mPresenter.update(aula)
        else
            mPresenter.add(aula)
    }
}