package com.wolfteam20.schedulemobile.ui.editardb.base

import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Efrain.Bastidas on 15/2/2018.
 */


abstract class ItemDetailsBasePresenter<TView : ItemDetailsBaseViewContract>(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<TView>(mCompositeDisposable, mDataManager),
    ItemDetailsBasePresenterContract {

    protected val DELETE_OPERATION = 0
    private val CANCEL_OPERATION = 1
    protected val ADD_OPERATION = 2
    protected val UPDATE_OPERATION = 3

    protected var isInEditMode = true
    protected var mItemID: Long = 0
    protected var mItemPosition: Int = 0

    override fun onCancelClicked() {
        viewState.finishActivity(CANCEL_OPERATION)
    }

    override fun onDeleteClicked() {
        viewState.showConfirmDeleteDialog()
    }

    override fun onSaveClicked() {
        viewState.prepareData(isInEditMode)
    }

    protected fun onError(error: Throwable) {
        viewState.hideLoading()
        viewState.onError(error.localizedMessage)
    }
}