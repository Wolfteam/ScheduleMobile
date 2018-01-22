package com.wolfteam20.schedulemobile.ui.disponibilidad

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresListSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsActivity
import kotlinx.android.synthetic.main.disponibilidad_fragment.*
import javax.inject.Inject


/**
 * Created by Efrain.Bastidas on 1/11/2018.
 */
class DispFragment : BaseFragment(), DispViewContract, AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var mPresenter: DispPresenterContract<DispViewContract>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.disponibilidad_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val edited = data?.getBooleanExtra("Edited", false) ?: false
                val cedula = disp_prof_dropdown.selectedItemId.toInt()

                if (edited && cedula != -1)
                    mPresenter.onHorasUpdatedLocal(cedula)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (id.toInt() == -1) {
            updateHoras(0, 0)
            enableAllButtons(false)
            return
        }
        enableAllButtons(true)
        mPresenter.onProfesorSelected(id.toInt())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context, "No has seleccionado nada todavia", Toast.LENGTH_LONG).show()
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        baseDrawerActivity.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(true)
            it.title = resources.getString(R.string.disp_activity)
        }
        enableAllButtons(false)
        mPresenter.onAttach(this)
        mPresenter.subscribe()
    }

    override fun hideLoading() {
        disp_progress_bar.visibility = View.GONE
    }

    @OnClick(R.id.btn_lunes, R.id.btn_martes, R.id.btn_miercoles, R.id.btn_jueves, R.id.btn_viernes, R.id.btn_sabado)
    override fun onBtnDiaClick(view: View) {
        when (view.id) {
            R.id.btn_lunes -> mPresenter.onDiaClicked(1)
            R.id.btn_martes -> mPresenter.onDiaClicked(2)
            R.id.btn_miercoles -> mPresenter.onDiaClicked(3)
            R.id.btn_jueves -> mPresenter.onDiaClicked(4)
            R.id.btn_viernes -> mPresenter.onDiaClicked(5)
            else -> mPresenter.onDiaClicked(6)
        }
    }

    @OnClick(R.id.btnGuardarCambios)
    override fun onBtnGuardarCambiosClick() {
        val horasRestantes = horas_restantes.text.toString().toInt()
        if (horasRestantes == 0)
            mPresenter.saveDisponibilidad(disp_prof_dropdown.selectedItemId.toInt())
        else
            Toast.makeText(baseDrawerActivity, "Aun queda $horasRestantes horas", Toast.LENGTH_LONG).show()
    }

    override fun startDetailsActivity(idDia: Int) {
        val intent = DispDetailsActivity.getIntent(context)
        intent.putExtra("Cedula", disp_prof_dropdown.selectedItemId.toInt())
        intent.putExtra("IdDia", idDia)
        startActivityForResult(intent, 1)
    }

    override fun showLoading() {
        disp_progress_bar.visibility = View.VISIBLE
    }

    override fun showProfesores(profesores: MutableList<ProfesorDetailsDTO>) {
        profesores.add(0, ProfesorDetailsDTO(-1, "Seleccione una opcion", "", null))
        val adapter = ProfesoresListSpinnerAdapter(baseDrawerActivity, R.layout.disponibilidad_spinner_prof_row, profesores)
        disp_prof_dropdown.adapter = adapter
        disp_prof_dropdown.onItemSelectedListener = this
        disp_prof_dropdown.setSelection(0)
    }

    override fun updateHoras(horasACumplir: Int, horasRestantes: Int) {
        horas_asignadas.setText(horasACumplir.toString())
        horas_restantes.setText(horasRestantes.toString())
    }

    private fun enableAllButtons(enable: Boolean) {
        btn_lunes.isEnabled = enable
        btn_martes.isEnabled = enable
        btn_miercoles.isEnabled = enable
        btn_jueves.isEnabled = enable
        btn_viernes.isEnabled = enable
        btn_sabado.isEnabled = enable
        btnGuardarCambios.isEnabled = enable
    }
}