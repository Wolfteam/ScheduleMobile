package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Efrain Bastidas on 1/13/2018.
 */

@InjectViewState
class DispDetailsPresenter : BasePresenter<DispDetailsViewContract>, DispDetailsPresenterContract {

    private var mDispList: MutableList<DisponibilidadDTO> = arrayListOf()
    private var mCedula = 0
    private var mIdDia: Int = 0
    private var mHorasAsignadas = 0
    private var mHorasACumplir = 0

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager)


    override fun addDisponibilidad(idHoraInicio: Int, idHoraFin: Int) {
        mHorasAsignadas += idHoraFin - idHoraInicio
        val disp = DisponibilidadDTO(0, mCedula, mIdDia, idHoraInicio, idHoraFin, -1)
        viewState.addItem(disp)
        //TODO: POR ALGUNA RAZON ALGUIEN ME AGREGA ITEMS o.o cuando agrego al adapter
        //mDispList.add(disp)
    }

    override fun onDisponibilidadDeleted(idHoraInicio: Int, idHoraFin: Int) {
        mHorasAsignadas -= idHoraFin - idHoraInicio
    }

    override fun saveDisponibilidadLocal() {
        dataManager.removeDisponibilidadLocal(mCedula, mIdDia)
        dataManager.removeDisponibilidadDetailsLocal(mCedula)
        dataManager.saveDisponibilidadLocal(mDispList)
        dataManager.saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO(0, mCedula, null, mHorasAsignadas, mHorasACumplir))
        viewState.showMessage(R.string.disp_details_save_msg)
    }

    override fun subscribe(cedula: Int, idDia: Int) {
        if (mDispList.size > 0)
            return
        mCedula = cedula
        mIdDia = idDia
        compositeDisposable.addAll(
                dataManager.getDisponibilidadDetailsLocal(cedula)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { details ->
                                    mHorasACumplir = details.horasACumplir
                                    mHorasAsignadas = details.horasAsignadas
                                }),
                dataManager.getDisponibilidadLocal(cedula, idDia)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { disp ->
                                    mDispList = disp
                                    viewState.showList(disp)
                                },
                                { throwable -> viewState.onError(throwable.localizedMessage) }
                        )
        )
    }

    override fun updateItems(disponibilidad: MutableList<DisponibilidadDTO>) {
       mDispList = disponibilidad
    }

    override fun validateHorasSelected(idHoraInicio: Int, idHoraFin: Int): Boolean {
        val horasRestantes = mHorasACumplir - mHorasAsignadas
        if (horasRestantes < idHoraFin - idHoraInicio) {
            viewState.onError("Excedes las horas restantes, solo puedes asignar: $horasRestantes horas mas")
            return false
        }
        if (mDispList.size == 0) {
            val result = validateHoras(idHoraInicio, idHoraFin)
            if (!result) {
                viewState.onError(R.string.disp_details_add_msg)
                return false
            }
        } else {
            for (d in mDispList) {
                val result = validateHoras(d.idHoraInicio, d.idHoraFin, idHoraInicio, idHoraFin)
                if (!result) {
                    viewState.onError(R.string.disp_details_add_msg)
                    return false
                }
            }
        }
        return true
    }

    /**
     * Valida las horas en base a las existentes.
     * Una hora no es valida cuando alguno de los datos da negativo y la otra da positiva.
     * Si alguna da cero, es valida.
     * Si ambas dan negativa es valida.
     * Agregue otra validacion para casos cuando una materia comienza seguida de otra. Debe ser testeado
     * Tambien verifica que las horas de inicio y fin no se encuentren en el rango del mediodia
     * @param [idHoraInicioDB] Id de la hora de inicio almacenada en la DB
     * @param [idHoraFinDB] Id de la hora de fin almacenada en la DB
     * @param [idHoraInicio] Id de la hora de inicio
     * @param [idHoraFin] Id de la hora de fin
     * @return True si las horas dadas son validas.
     */
    private fun validateHoras(idHoraInicioDB: Int, idHoraFinDB: Int, idHoraInicio: Int, idHoraFin: Int): Boolean {
        val result = validateHoras(idHoraInicio, idHoraFin)
        if (!result)
            return false
        if (idHoraFinDB == idHoraInicio)
            return true
        val dato1 = idHoraFinDB - idHoraInicio
        val dato2 = idHoraInicioDB - idHoraFin
        return !(dato1 >= 0 && dato2 < 0 || dato1 < 0 && dato2 >= 0)
    }

    /**
     * Valida las horas pasadas por parametro
     * @param [idHoraInicio] Id de la hora de inicio
     * @param [idHoraFin] Id de la hora de fin
     * @return True si las horas dadas son validas.
     */
    private fun validateHoras(idHoraInicio: Int, idHoraFin: Int): Boolean {
        if (idHoraFin - idHoraInicio <= 0)
            return false
        if (idHoraInicio <= 7 && idHoraFin > 7)
            return false
        if (idHoraFin - idHoraInicio < 2)
            return false
        return true
    }
}