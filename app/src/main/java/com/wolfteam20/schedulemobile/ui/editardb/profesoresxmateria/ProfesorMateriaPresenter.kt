package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemBasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.text.Collator
import java.util.*
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 2/5/2018.
 */

@InjectViewState
class ProfesorMateriaPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemBasePresenter<ProfesorMateriaViewContract>(mCompositeDisposable, mDataManager),
    ProfesorMateriaPresenterContract {

    private val detailsFragment = 5

    override fun subscribe() {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.allProfesorMateria
            .subscribe(
                { pm ->
                    val collator = Collator.getInstance(Locale.US)
                    pm.sortWith(Comparator { c1, c2 ->
                        collator.compare(c1.profesor.nombre, c2.profesor.nombre)
                    })
                    viewState.showList(pm)
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

    override fun onItemClicked(itemID: Long, itemPosition: Int, item: ProfesorMateriaDetailsDTO) {
        viewState.startDetailsActivity(detailsFragment, itemID, itemPosition, item)
    }

    override fun deleteItems(items: MutableList<ProfesorMateriaDetailsDTO>) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val ids = items.joinToString(
            ",",
            transform = { return@joinToString it.id.toString() }
        )
        viewState.showSwipeToRefresh()
        compositeDisposable.add(dataManager.removeProfesorMateria(ids)
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

    override fun onItemAdded() {
        subscribe()
        viewState.showSuccessMessage(R.string.relacion_created)
    }

    override fun onItemUpdated(item: ProfesorMateriaDetailsDTO, itemPosition: Int) {
        viewState.updateItem(itemPosition, item)
        viewState.showSuccessMessage(R.string.relacion_updated)
    }

    override fun onItemRemoved(position: Int) {
        viewState.removeItem(position)
        viewState.showSuccessMessage(R.string.relacion_deleted)
    }
}