package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
class DispDetailsPresenter<V : DispDetailsContractView>
    : BasePresenter<V>, DispDetailsContractPresenter<V> {
    @Inject
    constructor(mCompositeDisposable: CompositeDisposable, mDataManager: DataManagerContract)
            : super(mCompositeDisposable, mDataManager)

    override fun subscribe() {

    }
}