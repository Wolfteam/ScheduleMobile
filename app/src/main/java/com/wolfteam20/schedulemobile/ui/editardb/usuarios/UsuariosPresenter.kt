package com.wolfteam20.schedulemobile.ui.editardb.usuarios

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
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
class UsuariosPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<UsuariosViewContract>(mCompositeDisposable, mDataManager),
    UsuariosPresenterContract {

    private val detailsFragment = 7

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allUsuarios
            .subscribe(
                { usuarios ->
                    usuarios.sortBy { it.username }
                    viewState.showList(usuarios)
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

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: UsuarioDetailsDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun deleteItems(items: MutableList<UsuarioDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val cedulas = items.joinToString(
            ",",
            transform = { return@joinToString it.cedula.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeUsuarios(cedulas)
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

    override fun onItemAdded(item: UsuarioDetailsDTO) {
        viewState.addItem(item)
    }

    override fun onItemUpdated(item: UsuarioDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
    }
}