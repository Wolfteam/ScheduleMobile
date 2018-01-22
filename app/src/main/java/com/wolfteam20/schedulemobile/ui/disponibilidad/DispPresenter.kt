package com.wolfteam20.schedulemobile.ui.disponibilidad

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
class DispPresenter<V : DispViewContract> : BasePresenter<V>, DispPresenterContract<V> {

    private var mProfesores: MutableList<ProfesorDetailsDTO> = mutableListOf()

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager)

    override fun onDiaClicked(idDia: Int) {
        view.startDetailsActivity(idDia)
    }

    override fun onHorasUpdatedLocal(cedula: Int) {
        compositeDisposable.add(dataManager.getDisponibilidadDetailsLocal(cedula)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { details ->
                            view.updateHoras(details.horasACumplir, details.horasACumplir - details.horasAsignadas)
                        }
                )
        )
    }

    override fun onProfesorSelected(cedula: Int) {
        view.showLoading()
        compositeDisposable.add(dataManager.getDisponbilidad(cedula)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { disp ->
                            dataManager.removeDisponibilidadDetailsLocal(cedula)
                            dataManager.removeDisponibilidadLocal(cedula)
                            dataManager.saveDisponibilidadLocal(disp.disponibilidad)
                            dataManager.saveDisponibilidadDetailsLocal(DisponibilidadDetailsDTO(0, cedula, null, disp.horasAsignadas, disp.horasACumplir))
                            view.updateHoras(disp.horasACumplir, disp.horasACumplir - disp.horasAsignadas)
                        },
                        { throwable ->
                            view.hideLoading()
                            view.onError(throwable.message)
                            Timber.e(throwable)
                        },
                        { view.hideLoading() }
                )

        )
    }

    override fun saveDisponibilidad(cedula: Int) {
        if (!view.isNetworkAvailable) {
            view.onError(R.string.no_network)
            return
        }
        view.showLoading()
        compositeDisposable.add(dataManager.getDisponibilidadLocal(cedula)
                .flatMap { disp -> return@flatMap dataManager.postDisponibilidad(disp) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.isSuccessful)
                                view.showMessage(R.string.disp_save_msg)
                            else
                                view.showMessage(R.string.disp_error_msg)
                        },
                        { error ->
                            view.hideLoading()
                            Timber.e(error)
                            view.onError(error.localizedMessage)
                        },
                        { view.hideLoading() }
                )
        )
    }

    override fun subscribe() {
        if (mProfesores.size != 0) {
            view.showProfesores(mProfesores)
            return
        }
        if (!view.isNetworkAvailable) {
            view.onError(R.string.no_network)
            return
        }
        val isAdmin = dataManager.isUserAdmin
        view.showLoading()
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
                                    view.onError(R.string.no_prof_found)
                                else
                                    view.showProfesores(mProfesores)
                            },
                            { throwable ->
                                view.hideLoading()
                                view.onError(throwable.message)
                                Timber.e(throwable)
                            },
                            { view.hideLoading() }
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
                                view.showProfesores(mProfesores)
                            },
                            { throwable ->
                                view.hideLoading()
                                view.onError(throwable.message)
                                Timber.e(throwable)
                            },
                            { view.hideLoading() }
                    )
            )
    }
}