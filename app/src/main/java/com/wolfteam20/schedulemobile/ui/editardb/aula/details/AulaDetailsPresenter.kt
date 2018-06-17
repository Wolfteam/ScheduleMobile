package com.wolfteam20.schedulemobile.ui.editardb.aula.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.AulaDTO
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */

@InjectViewState
class AulaDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<AulaDetailsViewContract>(mCompositeDisposable, mDataManager),
    AulaDetailsPresenterContract {

    override fun subscribe(idAula: Long, position: Int, model: AulaDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        if (idAula == 0L) {
            isInEditMode = false
            compositeDisposable.add(dataManager.allTipoAulaMateria
                .subscribe(
                    { tipos -> initView(model, tipos) },
                    { error -> onError(error) }
                )
            )
            return
        }

        mItemID = model?.idAula!!
        mItemPosition = position
        compositeDisposable.add(dataManager.allTipoAulaMateria
            .subscribe(
                { tipos -> initView(model, tipos) },
                { error -> onError(error) }
            )
        )
    }

    override fun delete() {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.showLoading()
        compositeDisposable.add(dataManager.removeAula(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(aula: AulaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = AulaDTO(0, aula.nombreAula, aula.capacidad, aula.tipoAula.idTipo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.addAula(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(aula: AulaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        aula.idAula = mItemID
        val dto = AulaDTO(mItemID, aula.nombreAula, aula.capacidad, aula.tipoAula.idTipo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateAula(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, aula) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(aula: AulaDetailsDTO?, tipoAula: MutableList<TipoAulaMateriaDTO>) {
        viewState.enableAllViews(true)
        viewState.setTipoAulaSpinnerItems(tipoAula)
        viewState.hideLoading()
        viewState.showItem(aula)
    }
}