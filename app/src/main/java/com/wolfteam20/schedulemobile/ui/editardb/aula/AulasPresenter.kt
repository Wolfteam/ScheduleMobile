package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@InjectViewState
class AulasPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<AulasViewContract>(mCompositeDisposable, mDataManager), AulasPresenterContract {

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(
                dataManager.allAulas
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { aulas ->
                                aulas.forEach { aula -> aula.tipo.target = aula.tipoAula }
                                dataManager.removeAulasLocal()
                                dataManager.saveAulasLocal(aulas)
                                viewState.showList(aulas)
                                viewState.hideSwipeToRefresh()
                                viewState.showFAB()
                            },
                            { error ->
                                viewState.hideSwipeToRefresh()
                                viewState.onError(error.localizedMessage)
                                Timber.e(error)
                            }
                    )
        )
    }

    override fun onItemClicked(id: Long) {
        viewState.startDetailsActivity(id)
    }

    override fun onItemLongClicked(position: Int) {
        viewState.toggleItemSelection(position)
    }

    override fun onFABAddClicked() {
        viewState.startDetailsActivity(0)
    }

    override fun onFABDeleteClicked(aulas: MutableList<AulaDetailsDTO>) {
        if (aulas.size == 0) {
            viewState.showMessage("Debe seleccionar al menos un item")
            return
        }

        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val idAulas = aulas.joinToString(
                ",",
                transform = { return@joinToString it.idAula.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(
                dataManager.removeAulas(idAulas)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {
                                viewState.hideSwipeToRefresh()
                                viewState.removeSelectedListItems()
                            },
                            { error ->
                                viewState.hideSwipeToRefresh()
                                viewState.onError(error.localizedMessage)
                                Timber.e(error)
                            }
                    )
        )

    }
}