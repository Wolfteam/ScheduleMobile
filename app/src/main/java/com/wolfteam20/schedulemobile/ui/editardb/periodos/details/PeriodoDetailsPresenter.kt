package com.wolfteam20.schedulemobile.ui.editardb.periodos.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */

@InjectViewState
class PeriodoDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<PeriodoDetailsViewContract>(mCompositeDisposable, mDataManager),
    PeriodoDetailsPresenterContract {

    override fun subscribe(idPeriodo: Long, position: Int, model: PeriodoAcademicoDTO?) {
        if (idPeriodo == 0L){
            isInEditMode = false
            return
        }
        mItemID = idPeriodo
        mItemPosition = position
        viewState.showItem(model!!)
    }

    override fun delete() {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.removePeriodoAcademico(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(periodo: PeriodoAcademicoDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.addPeriodoAcademico(periodo)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, periodo) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(periodo: PeriodoAcademicoDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.updatePeriodoAcademico(mItemID, periodo)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, periodo) },
                { error -> onError(error) }
            )
        )
    }
}