package com.wolfteam20.schedulemobile.ui.editardb.secciones

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
 * Created by Efrain Bastidas on 2/5/2018.
 */

@InjectViewState
class SeccionesPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<SeccionesViewContract>(mCompositeDisposable, mDataManager),
    SeccionesPresenterContract {

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(
            dataManager.allSecciones
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { secciones ->
                        viewState.showList(secciones)
                        viewState.hideSwipeToRefresh()
                        viewState.showFAB()
                    },
                    { error ->
                        viewState.hideSwipeToRefresh()
                        viewState.onError(error.localizedMessage)
                        Timber.e(error)
                    })
        )
    }
}