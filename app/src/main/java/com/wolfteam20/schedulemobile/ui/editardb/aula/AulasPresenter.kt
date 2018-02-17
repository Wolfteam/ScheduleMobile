package com.wolfteam20.schedulemobile.ui.editardb.aula

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@InjectViewState
class AulasPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<AulasViewContract>(mCompositeDisposable, mDataManager),
    AulasPresenterContract {

    private val detailsFragment = 1

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allAulas
            .subscribe(
                { aulas ->
                    aulas.sortBy { it.nombreAula }
                    viewState.showList(aulas)
                    viewState.hideSwipeToRefresh()
                    viewState.showFAB()
                },
                { error -> onError(error) }
            )
        )
    }

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: AulaDetailsDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun onFABAddClicked() {
        viewState.startDetailsActivity(detailsFragment)
    }

    override fun deleteItems(aulas: MutableList<AulaDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val idAulas = aulas.joinToString(
            ",",
            transform = { return@joinToString it.idAula.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeAulas(idAulas)
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

    override fun onItemAdded(itemID: Long) {
        subscribe()
    }

    override fun onItemUpdated(item: AulaDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
    }

}