package com.wolfteam20.schedulemobile.ui.editardb.materia.details

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
import com.wolfteam20.schedulemobile.data.network.models.CarreraDTO
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.SemestreDTO
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO
import com.wolfteam20.schedulemobile.ui.adapters.CarrerasSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.adapters.SemestresSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.adapters.TipoAulaMateriaSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import com.wolfteam20.schedulemobile.utils.Constants
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_materias.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
class MateriaDetailsFragment : ItemDetailsBaseFragment(), MateriaDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: MateriaDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mCarreraAdapter: CarrerasSpinnerAdapter
    private lateinit var mSemestreAdapter: SemestresSpinnerAdapter
    private lateinit var mTipoMateriaAdapter: TipoAulaMateriaSpinnerAdapter

    @ProvidePresenter
    fun provideMateriaDetailsPresenter(): MateriaDetailsPresenter {
        activityComponent.inject(this)

        val id = baseActivity.intent.extras.getLong("ID", 0)
        val position = baseActivity.intent.extras.getInt("POSITION", 0)
        val model = baseActivity.intent.extras.getParcelable<MateriaDetailsDTO>("ITEM")
        mPresenter.subscribe(id, position, model)

        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_materias, container, false)
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

        mCarreraAdapter = CarrerasSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )
        mSemestreAdapter = SemestresSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )
        mTipoMateriaAdapter = TipoAulaMateriaSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )

        materia_details_carreras_dropdown.adapter = mCarreraAdapter
        materia_details_semestres_dropdown.adapter = mSemestreAdapter
        materia_details_tipo_dropdown.adapter = mTipoMateriaAdapter

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
        materia_details_codigo.isEnabled = enabled
        materia_details_carreras_dropdown.isEnabled = enabled
        materia_details_horas_semanales.isEnabled = enabled
        materia_details_horas_totales.isEnabled = enabled
        materia_details_nombre.isEnabled = enabled
        materia_details_semestres_dropdown.isEnabled = enabled
        materia_details_tipo_dropdown.isEnabled = enabled
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

    override fun setCarreraSpinnerItems(carreras: MutableList<CarreraDTO>) {
        mCarreraAdapter.setItems(carreras)
    }

    override fun prepareData(isInEditMode: Boolean) {
        val materia = getItemData() as MateriaDetailsDTO
        if (isInEditMode)
            mPresenter.update(materia)
        else
            mPresenter.add(materia)
    }

    override fun setTipoMateriaSpinnerItems(tipos: MutableList<TipoAulaMateriaDTO>) {
        mTipoMateriaAdapter.setItems(tipos)
    }

    override fun setSemestresSpinnerItems(semestres: MutableList<SemestreDTO>) {
        mSemestreAdapter.setItems(semestres)
    }

    override fun getItemData(): Parcelable {
        return MateriaDetailsDTO(
                materia_details_codigo.text.toString().toLongOrNull() ?: 0,
                materia_details_nombre.text.toString(),
                materia_details_horas_totales.text.toString().toIntOrNull() ?: 0,
                materia_details_horas_semanales.text.toString().toIntOrNull() ?: 0,
                mCarreraAdapter.getItem(materia_details_carreras_dropdown.selectedItemId),
                mSemestreAdapter.getItem(materia_details_semestres_dropdown.selectedItemId),
                mTipoMateriaAdapter.getItem(materia_details_tipo_dropdown.selectedItemId)
        )
    }

    override fun setItemData(item: Parcelable) {
        val materia = item as MateriaDetailsDTO
        val carreraPosition = mCarreraAdapter.getPosition(materia.carrera.idCarrera)
        val semestrePosition = mSemestreAdapter.getPosition(materia.semestre.idSemestre)
        val tipoPosition = mTipoMateriaAdapter.getPosition(materia.tipoMateria.idTipo)

        materia_details_semestres_dropdown.setSelection(semestrePosition)
        materia_details_carreras_dropdown.setSelection(carreraPosition)
        materia_details_tipo_dropdown.setSelection(tipoPosition)

        materia_details_codigo.setText(materia.codigo.toString())
        materia_details_nombre.setText(materia.asignatura)
        materia_details_horas_semanales.setText(materia.horasAcademicasSemanales.toString())
        materia_details_horas_totales.setText(materia.horasAcademicasTotales.toString())
    }
}