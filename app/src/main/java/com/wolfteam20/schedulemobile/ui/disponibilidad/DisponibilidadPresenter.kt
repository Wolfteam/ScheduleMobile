package com.wolfteam20.schedulemobile.ui.disponibilidad

import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
class DisponibilidadPresenter<V : DisponibilidadContractView>
    : BasePresenter<V>, DisponibilidadContractPresenter<V> {
    //TODO: ONERROR NO LLAMA A ONCOMPLETE CREO, de ser el caso debes ocultar los loading
    private var mProfesores: MutableList<ProfesorDetailsDTO> = mutableListOf()

    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager)

    override fun subscribe() {
//        val cedula
        val isAdmin = dataManager.isUserAdmin
        view.showLoading()
        if (isAdmin)
            compositeDisposable.add(dataManager.getAllProfesores()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { profesores ->
                                mProfesores = profesores
                                view.showProfesores(mProfesores)
                            },
                            { throwable ->
                                view.hideLoading()
                                view.showError(throwable.message)
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
                                mProfesores.add(ProfesorDetailsDTO(profesor.cedula, profesor.nombre, profesor.apellido, null))
                                view.showProfesores(mProfesores)
                            },
                            { throwable ->
                                view.hideLoading()
                                view.showError(throwable.message)
                                Timber.e(throwable)
                            },
                            { view.hideLoading() }
                    )
            )
    }
}