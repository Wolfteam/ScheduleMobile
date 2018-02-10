package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BasePresenter
import com.wolfteam20.schedulemobile.ui.editardb.details.EditarDBDetailsViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */

@InjectViewState
class AulasDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : BasePresenter<AulasDetailsViewContract>(mCompositeDisposable, mDataManager),
    AulasDetailsPresenterContract {

    lateinit var mAula: AulaDetailsDTO

    override fun subscribe(idAula: Long) {
        if (idAula.toInt() == 0)
            return
        viewState.showLoading()
        compositeDisposable.add(
            dataManager.getAulaLocal(idAula)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { aula ->
                        mAula = aula
                        viewState.showItem(aula)
                        viewState.hideLoading()
                    },
                    { error ->
                        viewState.hideLoading()
                        viewState.onError(error.localizedMessage)
                    }
                )
        )
        //aca tambien deberia jalarme la data para el spinner
    }

    override fun onDeleteClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}