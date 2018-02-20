package com.wolfteam20.schedulemobile.ui.editardb.profesores.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.PrioridadProfesorDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */

@InjectViewState
class ProfesorDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<ProfesorDetailsViewContract>(mCompositeDisposable, mDataManager),
    ProfesorDetailsPresenterContract {

    override fun subscribe(cedula: Long, position: Int, model: ProfesorDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        if (cedula == 0L) {
            isInEditMode = false
            compositeDisposable.add(dataManager.allPrioridades
                .subscribe(
                    { prioridades -> initView(prioridades) },
                    { error -> onError(error) }
                )
            )
            return
        }

        mItemID = cedula
        mItemPosition = position
        compositeDisposable.add(dataManager.allPrioridades
            .subscribe(
                { prioridades ->
                    initView(prioridades)
                    viewState.showItem(model!!)
                },
                { error -> onError(error) }
            )
        )
    }

    override fun delete() {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.removeProfesor(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(profesor: ProfesorDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = ProfesorDTO(
            profesor.cedula,
            profesor.nombre,
            profesor.apellido,
            profesor.prioridad!!.id
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.addProfesor(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, profesor) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(profesor: ProfesorDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = ProfesorDTO(
            profesor.cedula,
            profesor.nombre,
            profesor.apellido,
            profesor.prioridad!!.id
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateProfesor(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, profesor) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(prioridades: MutableList<PrioridadProfesorDTO>) {
        viewState.setPrioridadSpinnerItems(prioridades)
        viewState.enableAllViews(true)
        viewState.hideLoading()
    }
}