package com.wolfteam20.schedulemobile.ui.disponibilidad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.adapters.ProfesoresListSpinnerAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.disponibilidad_fragment.*
import javax.inject.Inject


/**
 * Created by Efrain.Bastidas on 1/11/2018.
 */
class DisponibilidadFragment : BaseFragment(), DisponibilidadContractView, AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var mPresenter: DisponibilidadContractPresenter<DisponibilidadContractView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.disponibilidad_fragment, container, false)
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(context, "Seleccionaste el item $id",Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context, "No has seleccionado nada todavia",Toast.LENGTH_LONG).show()
    }


    fun getStartIntent(context: Context): Intent {
        return Intent(context, DisponibilidadFragment::class.java)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        mPresenter.onAttach(this)
        mPresenter.subscribe()
    }

    override fun hideLoading() {

    }

    override fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        Toast.makeText(context, "Cargando...", Toast.LENGTH_LONG).show()
    }

    override fun showProfesores(profesores: MutableList<ProfesorDetailsDTO>) {
        val numeroProfesores = profesores.size
        if (numeroProfesores == 0) {
            showError("No se encontraron profesores")
            return
        } else if (numeroProfesores == 1) {
            horasAsignadas.setText(profesores.first().prioridad?.horasACumplir.toString())
        }
        val adapter = ProfesoresListSpinnerAdapter(context, android.R.layout.simple_spinner_dropdown_item, profesores)
        disp_prof_dropdown.adapter = adapter
        disp_prof_dropdown.onItemSelectedListener = this
    }
}