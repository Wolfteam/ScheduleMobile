package com.wolfteam20.schedulemobile.ui.editardb.periodos

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
@InjectViewState
class PeriodosPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<PeriodosViewContract>(mCompositeDisposable, mDataManager),
    PeriodosPresenterContract {


    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showLoading()
        compositeDisposable.add(
            dataManager.allPeriodosAcademicos
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { periodos ->
                        viewState.showList(periodos)
                        viewState.hideLoading()
                        viewState.showFAB()
                    },
                    { error ->
                        viewState.hideLoading()
                        viewState.onError(error.localizedMessage)
                        Timber.e(error)
                    })
        )
    }
}