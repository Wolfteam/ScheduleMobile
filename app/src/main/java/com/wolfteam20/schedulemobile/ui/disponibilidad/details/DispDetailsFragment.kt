package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.disponibilidad_details_fragment.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
class DispDetailsFragment : BaseFragment(), DispDetailsContractView {

    @Inject
    lateinit var mPresenter: DispDetailsContractPresenter<DispDetailsContractView>
    private var mDisponibilidad: DisponibilidadDetailsDTO? = null
    private var idDia: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.disponibilidad_details_fragment, container, false)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        mDisponibilidad = arguments?.getParcelable("Disponibilidad") //baseDrawerActivity.intent.extras.getParcelable<DisponibilidadDetailsDTO>("Disponibilidad")
        idDia = arguments?.getInt("IdDia") ?: -1  //baseActivity.intent.extras.getInt("IdDia")
        pruebas.text = "El dia seleccionado es $idDia"
        baseDrawerActivity.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = resources.getString(when (idDia) {
                1 -> R.string.dia_lunes
                2 -> R.string.dia_martes
                3 -> R.string.dia_miercoles
                4 -> R.string.dia_jueves
                5 -> R.string.dia_viernes
                else -> R.string.dia_sabado
            })
        }
    }

    //ESTO CREO Q NO ES NECESARIO SOLO EN ACTIVITIES?
    companion object {
        fun getIntent(context: Context, disponibilidad: DisponibilidadDetailsDTO, idDia: Int): Intent {
            val intent = Intent(context, DispDetailsFragment::class.java)
            intent.putExtra("Disponibilidad", disponibilidad)
            intent.putExtra("IdDia", idDia)
            return intent
        }
    }
}