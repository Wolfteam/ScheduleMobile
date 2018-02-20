package com.wolfteam20.schedulemobile.ui.editardb.secciones

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
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
class SeccionesPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<SeccionesViewContract>(mCompositeDisposable, mDataManager),
    SeccionesPresenterContract {

    private val detailsFragment = 6

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allSecciones
            .subscribe(
                { secciones ->
                    secciones.sortBy { it.materia.codigo }
                    viewState.showList(secciones)
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

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: SeccionDetailsDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun deleteItems(items: MutableList<SeccionDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val codigos = items.joinToString(
            ",",
            transform = { return@joinToString it.materia.codigo.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeSecciones(codigos)
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

    override fun onItemAdded(item: SeccionDetailsDTO) {
        viewState.addItem(item)
        viewState.showSuccessMessage(R.string.seccion_created)
    }

    override fun onItemUpdated(item: SeccionDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
        viewState.showSuccessMessage(R.string.seccion_updated)
    }

    override fun onItemRemoved(position: Int) {
        viewState.removeItem(position)
        viewState.showSuccessMessage(R.string.seccion_deleted)
    }
}