package com.wolfteam20.schedulemobile.ui.editardb.aula.details

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

    override fun subscribe(idAula: Long, position: Int, model: AulaDetailsDTO?) {
        viewState.enableAllViews(false)
        viewState.showLoading()

        if (idAula == 0L) {
            isInEditMode = false
            compositeDisposable.add(dataManager.allTipoAulaMateria
                .subscribe(
                    { tipos ->
                        viewState.enableAllViews(true)
                        viewState.setTipoAulaSpinnerItems(tipos)
                        viewState.hideLoading()
                    },
                    { error -> onError(error) }
                )
            )
            return
        }

        mIDAula = model?.idAula!!
        mAulaPosition = position

        compositeDisposable.add(dataManager.allTipoAulaMateria
            .subscribe(
                { tipos ->
                    viewState.enableAllViews(true)
                    viewState.setTipoAulaSpinnerItems(tipos)
                    viewState.hideLoading()
                    model.let {
                        viewState.showItem(it)
                    }
                },
                { error -> onError(error) }
            )
        )
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
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.removeAula(mIDAula)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mAulaPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun addAula(aula: AulaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = AulaDTO(0, aula.nombreAula, aula.capacidad, aula.tipoAula.idTipo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.addAula(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION) },
                { error -> onError(error) }
            )
        )
    }

    override fun updateAula(aula: AulaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        aula.idAula = mIDAula
        val dto = AulaDTO(mIDAula, aula.nombreAula, aula.capacidad, aula.tipoAula.idTipo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateAula(mIDAula, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mAulaPosition, aula) },
                { error -> onError(error) }
            )
        )
    }

    private fun onError(error: Throwable) {
        viewState.hideLoading()
        viewState.onError(error.localizedMessage)
    }
}