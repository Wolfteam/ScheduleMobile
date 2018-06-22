package com.wolfteam20.schedulemobile.ui.disponibilidad

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.Collator
import java.util.*
import javax.inject.Inject


/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */

@InjectViewState
class DispPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<DispViewContract>(mCompositeDisposable, mDataManager), DispPresenterContract {

    private var mProfesores: MutableList<ProfesorDetailsDTO> = mutableListOf()
    private var mSelectedCedula: Int = -1

    init {
        subscribe()
    }

    override fun onDiaClicked(idDia: Int) {
        viewState.startDetailsActivity(idDia)
    }

    override fun onHorasUpdatedLocal(cedula: Int) {
        compositeDisposable.add(dataManager.getDisponibilidadDetailsLocal(cedula)
            .subscribe(
                { details ->
                    viewState.updateHoras(
                        details.horasACumplir,
                        details.horasACumplir - details.horasAsignadas
                    )
                },
                { error ->
                    viewState.onError(error.localizedMessage)
                }
            )
        )
    }

    override fun onProfesorSelected(cedula: Int, position: Int, isActivityRecreated: Boolean) {
        if (cedula == -1 && !isActivityRecreated || mSelectedCedula == -1 && isActivityRecreated) {
            mSelectedCedula = cedula
            viewState.enableAllButtons(false)
            viewState.updateHoras(0, 0)
            return
        }
        viewState.showLoading()
        viewState.setItemSelected(position, false)
        if (mSelectedCedula != cedula && cedula > 0) {
            mSelectedCedula = cedula
            compositeDisposable.add(dataManager.getDisponbilidad(cedula)
                .subscribe(
                    { disp ->
                        dataManager.removeDisponibilidadDetailsLocal(cedula)
                        dataManager.removeDisponibilidadLocal(cedula)
                        dataManager.addDisponibilidadLocal(disp.disponibilidad)
                        dataManager.addDisponibilidadDetailsLocal(
                            DisponibilidadDetailsDTO(
                                0,
                                cedula,
                                null,
                                disp.horasAsignadas,
                                disp.horasACumplir
                            )
                        )
                        viewState.updateHoras(
                            disp.horasACumplir,
                            disp.horasACumplir - disp.horasAsignadas
                        )
                        viewState.enableAllButtons(true)
                    },
                    { throwable ->
                        viewState.hideLoading()
                        viewState.onError(throwable.message)
                        Timber.e(throwable)
                    },
                    { viewState.hideLoading() }
                )
            )
        } else {
            compositeDisposable.add(dataManager.getDisponibilidadDetailsLocal(mSelectedCedula)
                .subscribe(
                    { details ->
                        val horasRestantes = details.horasACumplir - details.horasAsignadas
                        viewState.updateHoras(details.horasACumplir, horasRestantes)
                        viewState.enableAllButtons(true)
                        viewState.hideLoading()
                    },
                    { throwable ->
                        viewState.hideLoading()
                        viewState.onError(throwable.message)
                        Timber.e(throwable)
                    }
                )
            )
        }
    }

    override fun saveDisponibilidad(cedula: Int) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showLoading()
        compositeDisposable.add(dataManager.getDisponibilidadLocal(cedula)
            .flatMap { disp -> return@flatMap dataManager.addDisponibilidad(disp) }
            .subscribe(
                { response ->
                    if (response.isSuccessful)
                        viewState.showMessage(R.string.disp_save_msg)
                    else
                        viewState.showMessage(R.string.disp_error_msg)
                },
                { error ->
                    viewState.hideLoading()
                    Timber.e(error)
                    viewState.onError(error.localizedMessage)
                },
                { viewState.hideLoading() }
            )
        )
    }

    override fun subscribe() {
        viewState.enableAllButtons(false)
        if (mProfesores.size != 0) {
            viewState.showProfesores(mProfesores)
            return
        }
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        val isAdmin = dataManager.isUserAdmin
        val default = ProfesorDetailsDTO()
        default.let {
            it.nombre = "Seleccione una opcion"
            it.cedula = -1
            it.apellido = ""
            it.prioridad = null
        }
        viewState.showLoading()
        if (isAdmin)
            compositeDisposable.add(dataManager.allProfesores
                .subscribe(
                    { profesores ->
                        mProfesores = profesores
                        val collator = Collator.getInstance(Locale.US)
                        mProfesores.sortWith(Comparator { c1, c2 ->
                            collator.compare(c1.nombre, c2.nombre)
                        })
                        mProfesores.add(0, default)
                        if (profesores.size == 0)
                            viewState.onError(R.string.no_prof_found)
                        else
                            viewState.showProfesores(mProfesores)
                        viewState.hideLoading()
                    },
                    { throwable ->
                        viewState.hideLoading()
                        viewState.onError(throwable.message)
                        Timber.e(throwable)
                    }
                )
            )
        else
            compositeDisposable.add(dataManager.getProfesor(dataManager.cedula)
                .subscribe(
                    { profesor ->
                        mProfesores.clear()
                        mProfesores.add(profesor)
                        mProfesores.add(0, default)
                        viewState.showProfesores(mProfesores)
                        viewState.hideLoading()
                    },
                    { throwable ->
                        viewState.hideLoading()
                        viewState.onError(throwable.message)
                        Timber.e(throwable)
                    }
                )
            )
    }
}