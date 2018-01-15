package com.wolfteam20.schedulemobile.ui.disponibilidad

import android.content.Context
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
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresListSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.disponibilidad_fragment.*
import javax.inject.Inject


/**
 * Created by Efrain.Bastidas on 1/11/2018.
 */
class DispFragment : BaseFragment(), DispContractView, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var mPresenter: DispContractPresenter<DispContractView>

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
        //El intent de disponibilidad details debe pedir la lista de prof
        Toasty.success(baseDrawerActivity, "Cambios guardados", Toast.LENGTH_SHORT).show()
    }

    override fun showDetailsFragment(disponibilidad: DisponibilidadDetailsDTO, idDia: Int) {
        val fragment = DispDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("Disponibilidad", disponibilidad)
        bundle.putInt("IdDia", idDia)
        fragment.arguments = bundle
        baseDrawerActivity.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.disponibilidad_activity, fragment, "DISP_DETAILS_FRAGMENT_TAG")
                .commit()
    }

    override fun showError(error: String) {
        Toasty.error(baseDrawerActivity, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        disp_progress_bar.visibility = View.VISIBLE
    }

    override fun showProfesores(profesores: MutableList<ProfesorDetailsDTO>) {
        val numeroProfesores = profesores.size
        if (numeroProfesores == 0) {
            showError("No se encontraron profesores")
            return
        }
        val adapter = ProfesoresListSpinnerAdapter(baseDrawerActivity, R.layout.disponibilidad_spinner_prof_row, profesores)
        disp_prof_dropdown.adapter = adapter
        disp_prof_dropdown.onItemSelectedListener = this
    }

    override fun updateHoras(horasACumplir: Int, horasRestantes: Int) {
        horas_asignadas.setText(horasACumplir.toString())
        horas_restantes.setText(horasRestantes.toString())
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, DispFragment::class.java)
        }
    }
}