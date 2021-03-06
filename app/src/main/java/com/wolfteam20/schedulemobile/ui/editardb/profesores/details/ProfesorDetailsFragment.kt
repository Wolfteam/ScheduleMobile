package com.wolfteam20.schedulemobile.ui.editardb.profesores.details

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
import com.wolfteam20.schedulemobile.data.network.models.PrioridadProfesorDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.PrioridadSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import com.wolfteam20.schedulemobile.utils.Constants
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_profesores.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class ProfesorDetailsFragment : ItemDetailsBaseFragment(), ProfesorDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: ProfesorDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mPrioridadesAdapter: PrioridadSpinnerAdapter

    @ProvidePresenter
    fun provideProfesorDetailsPresenter(): ProfesorDetailsPresenter {
        activityComponent.inject(this)

        val id = baseActivity.intent.extras.getLong(Constants.ITEM_ID_TAG, 0)
        val position = baseActivity.intent.extras.getInt(Constants.ITEM_POSITION_TAG, 0)
        val model = baseActivity.intent.extras.getParcelable<ProfesorDetailsDTO>(Constants.ITEM_TAG)
        mPresenter.subscribe(id, position, model)

        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_profesores, container, false)
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)!!
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

        mPrioridadesAdapter = PrioridadSpinnerAdapter(
                baseActivity,
                R.layout.editardb_details_common_spinner_layout
        )

        profesor_details_prioridad_dropdown.adapter = mPrioridadesAdapter

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
        profesor_details_nombre.isEnabled = enabled
        profesor_details_apellido.isEnabled = enabled
        profesor_details_cedula.isEnabled = enabled
        profesor_details_prioridad_dropdown.isEnabled = enabled
        mMenuItemsEnabled = enabled
        baseActivity.invalidateOptionsMenu()
    }

    override fun setPrioridadSpinnerItems(prioridades: MutableList<PrioridadProfesorDTO>) {
        mPrioridadesAdapter.setItems(prioridades)
    }

    override fun showConfirmDeleteDialog() {
        val dialog: AlertDialog = AlertDialog.Builder(baseActivity)
            .setTitle(resources.getString(R.string.are_you_sure))
            .setPositiveButton(
                    getString(R.string.yes), { _, _ ->
                mPresenter.delete()
            })
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }

    override fun prepareData(isInEditMode: Boolean) {
        val profesor = getItemData() as ProfesorDetailsDTO
        if (isInEditMode)
            mPresenter.update(profesor)
        else
            mPresenter.add(profesor)
    }

    override fun getItemData(): Parcelable {
        return ProfesorDetailsDTO(
                profesor_details_cedula.text.toString().toLongOrNull() ?: 0,
                profesor_details_nombre.text.toString(),
                profesor_details_apellido.text.toString(),
                mPrioridadesAdapter.getItem(profesor_details_prioridad_dropdown.selectedItemId)
        )
    }

    override fun setItemData(item: Parcelable) {
        val profesor = item as ProfesorDetailsDTO
        profesor_details_nombre.setText(profesor.nombre)
        profesor_details_apellido.setText(profesor.apellido)
        profesor_details_cedula.setText(profesor.cedula.toString())
        profesor_details_prioridad_dropdown.setSelection(mPrioridadesAdapter.getPosition(profesor.prioridad!!.id))
    }
}