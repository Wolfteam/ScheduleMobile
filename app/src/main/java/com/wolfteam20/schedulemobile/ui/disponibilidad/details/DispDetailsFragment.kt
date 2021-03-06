package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.NumberPicker
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.ui.adapters.DispDetailsListAdapter
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.disponibilidad_details_fragment.*
import javax.inject.Inject


/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
class DispDetailsFragment : BaseFragment(), DispDetailsViewContract,
        DispDetailsListAdapter.DispDetailsListViewHolder.ClickListener {

    @Inject
    @InjectPresenter
    lateinit var mPresenter: DispDetailsPresenter
    private val mHoras = arrayOf("7:00 am", "7:50 am", "8:40 am", "9:30 am", "10:20 am", "11:10 am",
            "12:00 pm", "1:00 pm", "1:50 pm", "2:40 pm", "3:30 pm", "4:20 pm", "5:10 pm", "6:00 pm")
    private lateinit var mAdapter: DispDetailsListAdapter

    @ProvidePresenter
    fun provideHomePresenter(): DispDetailsPresenter {
        activityComponent.inject(this)
        return mPresenter
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
            R.id.disp_borrar_ic -> {
                if (mAdapter.getSelectedItemCount() == 0) {
                    showMessage(R.string.disp_details_delete)
                    return true
                }
                val itemsToRemove = mAdapter.getItems(mAdapter.getSelectedItems())
                for (i in itemsToRemove)
                    mPresenter.onDisponibilidadDeleted(i.idHoraInicio, i.idHoraFin)
                mAdapter.removeItems(mAdapter.getSelectedItems())
            }
            else -> baseActivity.onBackPressed()
        }
        return true
    }

    override fun addItem(disponibilidad: DisponibilidadDTO) {
        mAdapter.addItem(disponibilidad)
    }

    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        val cedula = baseActivity.intent.getIntExtra("Cedula", -1)
        val idDia = baseActivity.intent.getIntExtra("IdDia", -1)
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
        mAdapter = DispDetailsListAdapter(mPresenter, mHoras, this)
        val llm = LinearLayoutManager(context)
        disp_details_recyclerView.layoutManager = llm
        disp_details_recyclerView.addItemDecoration(DividerItemDecoration(disp_details_recyclerView.context, llm.orientation))
        disp_details_recyclerView.itemAnimator = DefaultItemAnimator()
        disp_details_recyclerView.adapter = mAdapter

        disp_details_fab.setOnClickListener { showAddDisponibilidadDialog() }
        //TODO: ACA HAY QUE PENSAR COMO SACO ESTA LINEA
        mPresenter.subscribe(cedula, idDia)
    }

    private fun showAddDisponibilidadDialog() {
        val view = layoutInflater.inflate(R.layout.disponibilidad_details_add_dialog, null)
        val horaInicio = view.findViewById<NumberPicker>(R.id.disp_details_hora_inicio)
        val horaFin = view.findViewById<NumberPicker>(R.id.disp_details_hora_fin)

        horaInicio.minValue = 1
        horaInicio.maxValue = 14
        horaInicio.displayedValues = mHoras
        horaFin.minValue = 1
        horaFin.maxValue = 14
        horaFin.displayedValues = mHoras

        val dialog: AlertDialog = AlertDialog.Builder(baseActivity)
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

    /**
     * Selecciona/Deselecciona un item en la [position] indicada
     */
    private fun toggleSelection(position: Int) {
        mAdapter.toggleSelection(position)
    }

    override fun showList(disponibilidades: MutableList<DisponibilidadDTO>) {
        mAdapter.setItems(disponibilidades)
    }

    override fun onItemClicked(position: Int) {
        toggleSelection(position)
    }

    override fun onItemLongClicked(position: Int): Boolean {
        toggleSelection(position)
        return true
    }
}