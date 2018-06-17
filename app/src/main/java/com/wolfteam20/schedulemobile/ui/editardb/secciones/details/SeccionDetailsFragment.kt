package com.wolfteam20.schedulemobile.ui.editardb.secciones.details

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Parcelable
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
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.MateriasSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import com.wolfteam20.schedulemobile.utils.Constants
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_secciones.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class SeccionDetailsFragment : ItemDetailsBaseFragment(), SeccionDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: SeccionDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mMateriaAdapter: MateriasSpinnerAdapter

    @ProvidePresenter
    fun provideSeccionDetailsPresenter(): SeccionDetailsPresenter {
        activityComponent.inject(this)

        val id = baseActivity.intent.extras.getLong(Constants.ITEM_ID_TAG, 0)
        val position = baseActivity.intent.extras.getInt(Constants.ITEM_POSITION_TAG, 0)
        val model = baseActivity.intent.extras.getParcelable<SeccionDetailsDTO>(Constants.ITEM_TAG)
        mPresenter.subscribe(id, position, model)

        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_secciones, container, false)
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
        mValidator = Validator(binding)
        binding.root.setOnClickListener {
            mValidator.validate()
        }
        return view
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val appCompatActivity = baseActivity as AppCompatActivity
        appCompatActivity.setSupportActionBar(editardb_details_fragment_toolbar)

        val id = baseActivity.intent.extras.getLong(Constants.ITEM_ID_TAG, 0)
        if (id != 0L)
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.edit)
        else
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.add)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        mMateriaAdapter = MateriasSpinnerAdapter(
                baseActivity,
                R.layout.editardb_details_common_spinner_layout
        )

        seccion_details_materias_dropdown.adapter = mMateriaAdapter

        savedInstanceState?.let {
            mCurrentItem = it.getParcelable(Constants.CURRENT_ITEM_TAG)
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
        seccion_details_alumnos.isEnabled = enabled
        seccion_details_numero.isEnabled = enabled
        seccion_details_materias_dropdown.isEnabled = enabled
        mMenuItemsEnabled = enabled
        baseActivity.invalidateOptionsMenu()
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

    override fun setMateriaSpinnerItems(materias: MutableList<MateriaDetailsDTO>) {
        mMateriaAdapter.setItems(materias)
    }

    override fun prepareData(isInEditMode: Boolean) {
        val seccion = getItemData() as SeccionDetailsDTO
        if (isInEditMode)
            mPresenter.update(seccion)
        else
            mPresenter.add(seccion)
    }

    override fun getItemData(): Parcelable {
        val codigo = seccion_details_materias_dropdown.selectedItemId
        return SeccionDetailsDTO(
                seccion_details_numero.text.toString().toIntOrNull() ?: 0,
                seccion_details_alumnos.text.toString().toIntOrNull() ?: 0,
                mMateriaAdapter.getItem(codigo),
                null
        )
    }

    override fun setItemData(item: Parcelable) {
        val seccion = item as SeccionDetailsDTO
        seccion_details_alumnos.setText(seccion.cantidadAlumnos.toString())
        seccion_details_numero.setText(seccion.numeroSecciones.toString())
        seccion_details_materias_dropdown.setSelection(mMateriaAdapter.getPosition(seccion.materia.codigo))
    }
}