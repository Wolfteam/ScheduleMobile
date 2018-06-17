package com.wolfteam20.schedulemobile.ui.editardb.periodos.details

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
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import com.wolfteam20.schedulemobile.utils.Constants
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_periodo_acad.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class PeriodoDetailsFragment : ItemDetailsBaseFragment(), PeriodoDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: PeriodoDetailsPresenter
    private lateinit var mValidator: Validator

    @ProvidePresenter
    fun providePeriodoDetailsPresenter(): PeriodoDetailsPresenter {
        activityComponent.inject(this)

        val id = baseActivity.intent.extras.getLong(Constants.ITEM_ID_TAG, 0)
        val position = baseActivity.intent.extras.getInt(Constants.ITEM_POSITION_TAG, 0)
        val model = baseActivity.intent.extras.getParcelable<PeriodoAcademicoDTO>(Constants.ITEM_TAG)
        mPresenter.subscribe(id, position, model)

        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.editardb_details_fragment_periodo_acad, container, false)
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

        val id = baseActivity.intent.extras.getLong("ID", 0)
        if (id != 0L)
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.edit)
        else
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.add)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

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
        periodo_academico_details_nombre.isEnabled = enabled
        periodo_academico_details_status.isEnabled = enabled
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

    override fun prepareData(isInEditMode: Boolean) {
        val periodo = getItemData() as PeriodoAcademicoDTO
        if (isInEditMode)
            mPresenter.update(periodo)
        else
            mPresenter.add(periodo)
    }

    override fun getItemData(): Parcelable {
        return PeriodoAcademicoDTO(
                0,
                periodo_academico_details_nombre.text.toString(),
                periodo_academico_details_status.isChecked,
                Date()
        )
    }

    override fun setItemData(item: Parcelable) {
        val periodo = item as PeriodoAcademicoDTO
        periodo_academico_details_nombre.setText(periodo.nombrePeriodo)
        periodo_academico_details_status.isChecked = periodo.status
    }
}