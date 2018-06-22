package com.wolfteam20.schedulemobile.ui.editardb.profesores

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.text.Collator
import java.util.*
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
                    val collator = Collator.getInstance(Locale.US)
                    profesores.sortWith(Comparator { c1, c2 ->
                        collator.compare(c1.nombre, c2.nombre)
                    })
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
                    viewState.showSuccessMessage(R.string.items_deleted)
                },
                { error -> onError(error) }
            )
        )
    }

    override fun onItemAdded(item: ProfesorDetailsDTO) {
        viewState.addItem(item)
        viewState.showSuccessMessage(R.string.profesor_created)
    }

    override fun onItemUpdated(item: ProfesorDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
        viewState.showSuccessMessage(R.string.profesor_updated)
    }

    override fun onItemRemoved(position: Int) {
        viewState.removeItem(position)
        viewState.showSuccessMessage(R.string.profesor_deleted)
    }
}