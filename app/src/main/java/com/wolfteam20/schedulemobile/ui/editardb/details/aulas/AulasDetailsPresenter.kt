package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import com.wolfteam20.schedulemobile.ui.editardb.details.EditarDBDetailsViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.FieldPosition
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
private const val DELETE_OPERATION = 0
private const val CANCEL_OPERATION = 1
private const val ADD_OPERATION = 2

@InjectViewState
class AulasDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<AulasDetailsViewContract>(mCompositeDisposable, mDataManager),
    AulasDetailsPresenterContract {

    private lateinit var mAula: AulaDetailsDTO
    private var mAulaPosition: Int = 0

    override fun subscribe(idAula: Long, position: Int) {
        if (idAula == 0L)
            return
        mAulaPosition = position
        viewState.showLoading()
        compositeDisposable.add(
            dataManager.getAulaLocal(idAula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { aula ->
                        mAula = aula
                        viewState.showItem(aula)
                        viewState.hideLoading()
                    },
                    { error ->
                        viewState.hideLoading()
                        viewState.onError(error.localizedMessage)
                    }
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

    }

    override fun deleteAula() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showLoading()
        compositeDisposable.add(
            dataManager.removeAula(mAula.idAula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        dataManager.removeAulaLocal(mAula.idAula)
                        viewState.finishActivity(DELETE_OPERATION, mAulaPosition)
                    },
                    { error ->
                        viewState.hideKeyboard()
                        Timber.e(error)
                        viewState.onError(error.localizedMessage)
                    })
        )
    }
}