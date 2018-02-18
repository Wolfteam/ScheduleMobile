package com.wolfteam20.schedulemobile.ui.editardb.periodos

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
@InjectViewState
class PeriodosPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<PeriodosViewContract>(mCompositeDisposable, mDataManager),
    PeriodosPresenterContract {

    private val detailsFragment = 3

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(
            dataManager.allPeriodosAcademicos
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { periodos ->
                        periodos.sortBy { it.nombrePeriodo }
                        viewState.showList(periodos)
                        viewState.hideSwipeToRefresh()
                        viewState.showFAB()
                    },
                    { error -> onError(error) }
                )
        )
    }

    override fun onFABAddClicked() {
        viewState.startDetailsActivity(detailsFragment)
    }

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: PeriodoAcademicoDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun deleteItems(items: MutableList<PeriodoAcademicoDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val idPeriodos = items.joinToString(
            ",",
            transform = { return@joinToString it.idPeriodo.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removePeriodosAcademicos(idPeriodos)
            .subscribe(
                {
                    viewState.hideSwipeToRefresh()
                    viewState.removeSelectedListItems()
                    viewState.stopActionMode()
                },
                { error -> onError(error) }
            )
        )
    }

    override fun onItemAdded() {
        subscribe()
        viewState.showSuccessMessage(R.string.periodo_academico_created)
    }

    override fun onItemUpdated(item: PeriodoAcademicoDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
        viewState.showSuccessMessage(R.string.periodo_academico_updated)
    }

    override fun onItemRemoved(position: Int) {
        viewState.removeItem(position)
        viewState.showSuccessMessage(R.string.periodo_academico_deleted)
    }
}