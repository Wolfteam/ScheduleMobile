package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria

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
class ProfesorMateriaPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<ProfesorMateriaViewContract>(mCompositeDisposable, mDataManager),
    ProfesorMateriaPresenterContract {


    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(
            dataManager.allProfesorMateria
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { pm ->
                        viewState.showList(pm)
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