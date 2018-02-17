package com.wolfteam20.schedulemobile.ui.editardb.materia

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@InjectViewState
class MateriasPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<MateriasViewContract>(mCompositeDisposable, mDataManager),
    MateriasPresenterContract {

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allMaterias
            .subscribe(
                { materias ->
                    materias.sortBy { it.asignatura }
                    viewState.showList(materias)
                    viewState.hideSwipeToRefresh()
                    viewState.showFAB()
                },
                { error -> super.onError(error) }
            )
        )
    }

    override fun onFABAddClicked() {
        viewState.startDetailsActivity(2)
    }

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: MateriaDetailsDTO) {
        viewState.startDetailsActivity(2, itemID, itemPosition, item)
    }

    override fun deleteItems(materias: MutableList<MateriaDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val codigos = materias.joinToString(
            ",",
            transform = { return@joinToString it.codigo.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeMaterias(codigos)
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

    override fun onItemAdded(item: MateriaDetailsDTO) {
        viewState.addItem(item)
    }

    override fun onItemUpdated(item: MateriaDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
    }
}