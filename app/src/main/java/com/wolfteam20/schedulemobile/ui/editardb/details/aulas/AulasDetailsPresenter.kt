package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDTO
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
private const val DELETE_OPERATION = 0
private const val CANCEL_OPERATION = 1
private const val ADD_OPERATION = 2
private const val UPDATE_OPERATION = 3

@InjectViewState
class AulasDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<AulasDetailsViewContract>(mCompositeDisposable, mDataManager),
    AulasDetailsPresenterContract {

    private var mIDAula: Long = 0
    private var mAulaPosition: Int = 0
    private var isInEditMode = true

    override fun subscribe(idAula: Long, position: Int) {
        if (idAula == 0L) {
            isInEditMode = false
            return
        }
        mAulaPosition = position
        viewState.showLoading()
        compositeDisposable.add(dataManager.getAulaLocal(idAula)
            .subscribe(
                { aula ->
                    mIDAula = aula.idAula
                    viewState.showItem(aula)
                    viewState.hideLoading()
                },
                { error -> onError(error) }
            )
        )
        //aca tambien deberia jalarme la data para el spinner
    }

    override fun onCancelClicked() {
        viewState.finishActivity(CANCEL_OPERATION)
    }

    override fun onDeleteClicked() {
        viewState.showConfirmDeleteDialog()
    }

    override fun onSaveClicked() {
        viewState.prepareData(isInEditMode)
    }

    override fun deleteAula() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showLoading()
        viewState.hideKeyboard()
        compositeDisposable.add(dataManager.removeAula(mIDAula)
            .subscribe(
                {
                    dataManager.removeAulaLocal(mIDAula)
                    viewState.finishActivity(DELETE_OPERATION, mAulaPosition)
                },
                { error -> onError(error) }
            )
        )
    }

    override fun addAula(aula: AulaDTO) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val aulaDetails = getAulaDetails(aula)
        viewState.showLoading()
        viewState.hideKeyboard()
        compositeDisposable.add(dataManager.addAula(aula)
            .subscribe(
                {
                    dataManager.addAulaLocal(aulaDetails)
                    viewState.finishActivity(ADD_OPERATION)
                },
                { error -> onError(error) }
            )
        )
    }

    override fun updateAula(aula: AulaDTO) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val aulaDetails = getAulaDetails(aula)
        viewState.showLoading()
        viewState.hideKeyboard()
        compositeDisposable.add(dataManager.updateAula(mIDAula, aula)
            .subscribe(
                {
                    dataManager.updateAulaLocal(aulaDetails)
                    viewState.finishActivity(UPDATE_OPERATION, mAulaPosition)
                },
                { error -> onError(error) }
            )
        )
    }

    private fun getAulaDetails(aula: AulaDTO): AulaDetailsDTO {
        val aulaDetails = AulaDetailsDTO()
        aulaDetails.idAula = mIDAula
        aulaDetails.capacidad = aula.capacidad
        aulaDetails.nombreAula = aula.nombreAula
        aulaDetails.tipo.targetId = 1
        return aulaDetails
    }

    private fun onError(error: Throwable) {
        viewState.hideLoading()
        viewState.onError(error.localizedMessage)
    }
}