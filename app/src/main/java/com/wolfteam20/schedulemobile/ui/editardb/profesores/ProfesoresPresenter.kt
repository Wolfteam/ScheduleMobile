package com.wolfteam20.schedulemobile.ui.editardb.profesores

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */

@InjectViewState
class ProfesoresPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<ProfesoresViewContract>(mCompositeDisposable, mDataManager),
    ProfesoresPresenterContract {

    private val detailsFragment = 4

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allProfesores
            .subscribe(
                { profesores ->
                    profesores.sortBy { it.nombre }
                    viewState.showList(profesores)
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

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: ProfesorDetailsDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun deleteItems(items: MutableList<ProfesorDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val cedulas = items.joinToString(
            ",",
            transform = { return@joinToString it.cedula.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeProfesores(cedulas)
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

    override fun onItemAdded(item: ProfesorDetailsDTO) {
        viewState.addItem(item)
    }

    override fun onItemUpdated(item: ProfesorDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
    }
}