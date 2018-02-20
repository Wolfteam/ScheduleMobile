package com.wolfteam20.schedulemobile.ui.editardb.usuarios.details

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
import com.wolfteam20.schedulemobile.data.network.models.PrivilegioDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.PrivilegioSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresSpinnerAdapter2
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*
import kotlinx.android.synthetic.main.editardb_details_fragment_usuarios.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
class UsuarioDetailsFragment : ItemDetailsBaseFragment(), UsuarioDetailsViewContract {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: UsuarioDetailsPresenter
    private lateinit var mValidator: Validator
    private lateinit var mProfesoresAdapter: ProfesoresSpinnerAdapter2
    private lateinit var mPrivilegiosAdapter: PrivilegioSpinnerAdapter

    @ProvidePresenter
    fun provideUsuarioDetailsPresenter(): UsuarioDetailsPresenter {
        activityComponent.inject(this)
        return mPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.editardb_details_fragment_usuarios, container, false)
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
        val model = baseActivity.intent.extras.getParcelable<UsuarioDetailsDTO>("ITEM")

        val appCompatActivity = baseActivity as AppCompatActivity
        appCompatActivity.setSupportActionBar(editardb_details_fragment_toolbar)
        if (id != 0L)
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.edit)
        else
            appCompatActivity.supportActionBar?.title = resources.getString(R.string.add)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        mProfesoresAdapter = ProfesoresSpinnerAdapter2(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )

        mPrivilegiosAdapter = PrivilegioSpinnerAdapter(
            baseActivity,
            R.layout.editardb_details_common_spinner_layout
        )

        usuario_details_profesores_dropdown.adapter = mProfesoresAdapter
        usuario_details_privilegios_dropdown.adapter = mPrivilegiosAdapter

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

    override fun enableAllViews(enabled: Boolean) {
        usuario_details_username.isEnabled = enabled
        usuario_details_password.isEnabled = enabled
        usuario_details_privilegios_dropdown.isEnabled = enabled
        usuario_details_profesores_dropdown.isEnabled = enabled
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

    override fun showItem(usuario: UsuarioDetailsDTO) {
        usuario_details_username.setText(usuario.username)
        usuario_details_privilegios_dropdown.setSelection(mPrivilegiosAdapter.getPosition(usuario.privilegios.idPrivilegio))
        usuario_details_profesores_dropdown.setSelection(mProfesoresAdapter.getPosition(usuario.cedula))
    }

    override fun setProfesorSpinnerItems(profesores: MutableList<ProfesorDetailsDTO>) {
        mProfesoresAdapter.setItems(profesores)
    }

    override fun setPrivilegioSpinnerItems(prioridades: MutableList<PrivilegioDTO>) {
        mPrivilegiosAdapter.setItems(prioridades)
    }

    override fun prepareData(isInEditMode: Boolean) {
        val cedula = usuario_details_profesores_dropdown.selectedItemId
        val privilegio = usuario_details_privilegios_dropdown.selectedItemId
        val usuario = UsuarioDetailsDTO(
            cedula,
            usuario_details_username.text.toString(),
            usuario_details_password.text.toString(),
            null, mPrivilegiosAdapter.getItem(privilegio)
        )

        if (isInEditMode)
            mPresenter.update(usuario)
        else
            mPresenter.add(usuario)
    }
}