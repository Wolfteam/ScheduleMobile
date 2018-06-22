package com.wolfteam20.schedulemobile.ui.editardb.secciones.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.SeccionDTO
import com.wolfteam20.schedulemobile.data.network.models.SeccionDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */

@InjectViewState
class SeccionDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<SeccionDetailsViewContract>(mCompositeDisposable, mDataManager),
    SeccionDetailsPresenterContract {

    override fun subscribe(codigo: Long, position: Int, model: SeccionDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        if (codigo == 0L) {
            isInEditMode = false
            compositeDisposable.add(dataManager.allMaterias
                .subscribe(
                    { materias -> initView(model, materias) },
                    { error -> onError(error) }
                )
            )
            return
        }

        mItemID = codigo
        mItemPosition = position
        compositeDisposable.add(dataManager.allMaterias
            .subscribe(
                { materias -> initView(model, materias) },
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
        compositeDisposable.add(dataManager.removeSeccion(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(seccion: SeccionDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = SeccionDTO(seccion.materia.codigo, seccion.numeroSecciones, seccion.cantidadAlumnos, 0)

        viewState.showLoading()
        compositeDisposable.add(dataManager.addSeccion(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, seccion) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(seccion: SeccionDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = SeccionDTO(seccion.materia.codigo, seccion.numeroSecciones, seccion.cantidadAlumnos, 0)

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateSeccion(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, seccion) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(seccion: SeccionDetailsDTO?, materias: MutableList<MateriaDetailsDTO>) {
        materias.sortBy { it.asignatura }
        viewState.enableAllViews(true)
        viewState.setMateriaSpinnerItems(materias)
        viewState.hideLoading()
        viewState.showItem(seccion)
    }
}