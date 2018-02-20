package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.details

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
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.MateriasSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresSpinnerAdapter2
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_prof_mat.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class ProfesorMateriaDetailsFragmnet : ItemDetailsBaseFragment(),
    ProfesorMateriaDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: ProfesorMateriaDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mProfesorAdapter: ProfesoresSpinnerAdapter2
    private lateinit var mMateriaAdapter: MateriasSpinnerAdapter

    @ProvidePresenter
    fun provideProfesorMateriaDetailsPresenter(): ProfesorMateriaDetailsPresenter {
        activityComponent.inject(this)
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_prof_mat, container, false)
        val binding = DataBindingUtil.bind<ViewDataBinding>(view)
        mValidator = Validator(binding)
        binding.root.setOnClickListener {
            mValidator.validate()
        }
        return view
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val id = baseActivity.intent.extras.getLong("ID", 0)
        val position = baseActivity.intent.extras.getInt("POSITION", 0)
        val model = baseActivity.intent.extras.getParcelable<ProfesorMateriaDetailsDTO>("ITEM")

        val appCompatActivity = baseActivity as AppCompatActivity
        appCompatActivity.setSupportActionBar(editardb_details_fragment_toolbar)
        if (id != 0L)
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.edit)
        else
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.add)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        mProfesorAdapter = ProfesoresSpinnerAdapter2(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )

        mMateriaAdapter = MateriasSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )

        prof_mat_details_profesores_dropdown.adapter = mProfesorAdapter
        prof_mat_details_materias_dropdown.adapter = mMateriaAdapter

        mPresenter.subscribe(id, position, model)
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

    override fun showItem(relacion: ProfesorMateriaDetailsDTO) {
        prof_mat_details_profesores_dropdown.setSelection(mProfesorAdapter.getPosition(relacion.profesor.cedula))
        prof_mat_details_materias_dropdown.setSelection(mMateriaAdapter.getPosition(relacion.materia.codigo))
    }

    override fun enableAllViews(enabled: Boolean) {
        prof_mat_details_profesores_dropdown.isEnabled = enabled
        prof_mat_details_materias_dropdown.isEnabled = enabled
    }

    override fun setProfesoresSpinnerItems(profesores: MutableList<ProfesorDetailsDTO>) {
        mProfesorAdapter.setItems(profesores)
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

    override fun setMateriasSpinnerItems(materias: MutableList<MateriaDetailsDTO>) {
        mMateriaAdapter.setItems(materias)
    }

    override fun prepareData(isInEditMode: Boolean) {
        val relacion = ProfesorMateriaDetailsDTO(
            0,
            mProfesorAdapter.getItem(prof_mat_details_profesores_dropdown.selectedItemId),
            mMateriaAdapter.getItem(prof_mat_details_materias_dropdown.selectedItemId)
        )

        if (isInEditMode)
            mPresenter.update(relacion)
        else
            mPresenter.add(relacion)
    }
}