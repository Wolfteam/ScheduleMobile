package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.adapters.DispDetailsListAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.disponibilidad_details_fragment.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
class DispDetailsFragment : BaseFragment(), DispDetailsViewContract {
    @Inject
    lateinit var mPresenter: DispDetailsPresenterContract<DispDetailsViewContract>
    private val mHoras = arrayOf("7:00 am", "7:50 am", "8:40 am", "9:30 am", "10:20 am", "11:10 am",
            "12:00 pm", "1:00 pm", "1:50 pm", "2:40 pm", "3:30 pm", "4:20 pm", "5:10 pm", "6:00 pm")
    private var mAdapter = DispDetailsListAdapter(mHoras)
    val horaInicio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.disponibilidad_details_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.disp_details_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.disp_guardar_ic -> mPresenter.saveDisponibilidadLocal()
            R.id.disp_borrar_ic -> Toast.makeText(context, "Borrar", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(context, "NPI", Toast.LENGTH_LONG).show()
        }
        return true
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        val cedula = activity.intent.getIntExtra("Cedula", -1)
        val idDia = activity.intent.getIntExtra("IdDia", -1)

        //Al pasarle true me hace una llamada directa a onCreateOptionsMenu
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(disp_details_fragment_toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(when (idDia) {
            1 -> R.string.dia_lunes
            2 -> R.string.dia_martes
            3 -> R.string.dia_miercoles
            4 -> R.string.dia_jueves
            5 -> R.string.dia_viernes
            else -> R.string.dia_sabado
        })
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val llm = LinearLayoutManager(context)
        disp_details_recyclerView.layoutManager = llm
        disp_details_recyclerView.addItemDecoration(DividerItemDecoration(disp_details_recyclerView.context, llm.orientation))
        disp_details_recyclerView.itemAnimator = DefaultItemAnimator()
        disp_details_recyclerView.adapter = mAdapter

        disp_details_fab.setOnClickListener { showAddDisponibilidadDialog() }

        mPresenter.onAttach(this)
        mPresenter.subscribe(cedula, idDia)
    }

    private fun showAddDisponibilidadDialog() {
        val view = layoutInflater.inflate(R.layout.disp_details_add_dialog, null)
        val horaInicio = view.findViewById<NumberPicker>(R.id.disp_details_hora_inicio)
        val horaFin = view.findViewById<NumberPicker>(R.id.disp_details_hora_fin)

        horaInicio.minValue = 1
        horaInicio.maxValue = 14
        horaInicio.displayedValues = mHoras
        horaFin.minValue = 1
        horaFin.maxValue = 14
        horaFin.displayedValues = mHoras

        val dialog: AlertDialog = AlertDialog.Builder(this.context)
                .setTitle(resources.getString(R.string.disp_details_agregar)).setView(view)
                .setPositiveButton(getString(R.string.save), null)
                .setNegativeButton(getString(R.string.cancelar), null)
                .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val isValid = mPresenter.validateHorasSelected(horaInicio.value, horaFin.value)
            if (isValid) {
                mPresenter.addDisponibilidad(horaInicio.value, horaFin.value)
                dialog.dismiss()
            }

        }
    }

    override fun showEmptyList() {
        Toast.makeText(baseActivity, "No se encontraron registros", Toast.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Toast.makeText(baseActivity, error, Toast.LENGTH_LONG).show()
    }

    override fun showList(disponibilidades: List<DisponibilidadDTO>) {
        mAdapter.addDisponibilidades(disponibilidades)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(baseActivity, msg, Toast.LENGTH_SHORT).show()
    }
}