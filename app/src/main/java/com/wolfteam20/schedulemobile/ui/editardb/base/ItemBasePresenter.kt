package com.wolfteam20.schedulemobile.ui.editardb.base

import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Efrain.Bastidas on 13/2/2018.
 */
abstract class ItemBasePresenter<T : ItemBaseViewContract>(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<T>(mCompositeDisposable, mDataManager),
    ItemBasePresenterContract {

    override fun onItemLongClicked(itemPosition: Int) {
        viewState.toggleItemSelection(itemPosition)
    }

    override fun onFABDeleteClicked(itemsSelected: Int) {
        if (itemsSelected == 0)
            viewState.showNoItemsSelected()
        else
            viewState.showConfirmDelete()
    }

    override fun onActionMode() {
        viewState.startActionMode()
    }

    override fun onToggleItemSelection(itemsToSelect: Int) {
        if (itemsToSelect == 0)
            viewState.stopActionMode()
        else
            viewState.setActionModeTitle(itemsToSelect.toString())
    }

    protected fun onError(error: Throwable) {
        viewState.hideSwipeToRefresh()
        viewState.onError(error.localizedMessage)
    }
}