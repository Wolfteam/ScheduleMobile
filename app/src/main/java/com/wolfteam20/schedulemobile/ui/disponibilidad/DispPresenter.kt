package com.wolfteam20.schedulemobile.ui.disponibilidad

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
class DispPresenter<V : DispContractView>
    : BasePresenter<V>, DispContractPresenter<V> {

    private var mProfesores: MutableList<ProfesorDetailsDTO> = mutableListOf()

    //Esta disponibilidad es de un profesor en particular
    private var mDisponibilidad: DisponibilidadDetailsDTO? = null


    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager)

    override fun onDiaClicked(idDia: Int) {
        view.showDetailsFragment(mDisponibilidad, idDia)
    }

    override fun subscribe() {
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
                                Collections.sort(mProfesores) { c1, c2 ->
                                    collator.compare(c1.nombre, c2.nombre)
                                }
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
                                mProfesores.add(profesor)
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

    override fun onProfesorSelected(cedula: Int) {
        view.showLoading()
        compositeDisposable.add(dataManager.getDisponbilidad(cedula)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { disp ->
                            mDisponibilidad = disp
                            if (disp.disponibilidad == null)
                                view.updateHoras(disp.horasACumplir, disp.horasACumplir)
                            else
                                view.updateHoras(disp.horasACumplir, disp.horasACumplir - disp.horasAsignadas)
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