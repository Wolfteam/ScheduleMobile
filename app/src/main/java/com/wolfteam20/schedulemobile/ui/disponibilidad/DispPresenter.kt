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
class DispPresenter : BasePresenter<DispViewContract>, DispPresenterContract {

    private var mProfesores: MutableList<ProfesorDetailsDTO> = mutableListOf()

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager) {
        subscribe()
    }

    override fun onDiaClicked(idDia: Int) {
        viewState.startDetailsActivity(idDia)
    }

    override fun onHorasUpdatedLocal(cedula: Int) {
        compositeDisposable.add(dataManager.getDisponibilidadDetailsLocal(cedula)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { details ->
                            viewState.updateHoras(details.horasACumplir, details.horasACumplir - details.horasAsignadas)
                        }
                )
        )
    }

    override fun onProfesorSelected(cedula: Int) {
        viewState.showLoading()
        compositeDisposable.add(dataManager.getDisponbilidad(cedula)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { disp ->
                            dataManager.removeDisponibilidadDetailsLocal(cedula)
                            dataManager.removeDisponibilidadLocal(cedula)
                            dataManager.saveDisponibilidadLocal(disp.disponibilidad)
                            dataManager.saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO(0, cedula, null, disp.horasAsignadas, disp.horasACumplir))
                            viewState.updateHoras(disp.horasACumplir, disp.horasACumplir - disp.horasAsignadas)
                        },
                        { throwable ->
                            viewState.hideLoading()
                            viewState.onError(throwable.message)
                            Timber.e(throwable)
                        },
                        { viewState.hideLoading() }
                )

        )
    }

    override fun saveDisponibilidad(cedula: Int) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showLoading()
        compositeDisposable.add(dataManager.getDisponibilidadLocal(cedula)
                .flatMap { disp -> return@flatMap dataManager.postDisponibilidad(disp) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        if (mProfesores.size != 0) {
            viewState.showProfesores(mProfesores)
            return
        }
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        val isAdmin = dataManager.isUserAdmin
        viewState.showLoading()
        if (isAdmin)
            compositeDisposable.add(dataManager.getAllProfesores()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { profesores ->
                                mProfesores = profesores
                                val collator = Collator.getInstance(Locale.US)
                                mProfesores.sortWith(Comparator { c1, c2 ->
                                    collator.compare(c1.nombre, c2.nombre)
                                })
                                if (profesores.size == 0)
                                    viewState.onError(R.string.no_prof_found)
                                else
                                    viewState.showProfesores(mProfesores)
                            },
                            { throwable ->
                                viewState.hideLoading()
                                viewState.onError(throwable.message)
                                Timber.e(throwable)
                            },
                            { viewState.hideLoading() }
                    )
            )
        else
            compositeDisposable.add(dataManager.getProfesor(dataManager.cedula)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { profesor ->
                                mProfesores.clear()
                                mProfesores.add(profesor)
                                viewState.showProfesores(mProfesores)
                            },
                            { throwable ->
                                viewState.hideLoading()
                                viewState.onError(throwable.message)
                                Timber.e(throwable)
                            },
                            { viewState.hideLoading() }
                    )
            )
    }
}