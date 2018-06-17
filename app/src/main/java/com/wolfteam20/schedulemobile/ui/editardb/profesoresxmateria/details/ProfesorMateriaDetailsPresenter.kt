package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.text.Collator
import java.util.*
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
@InjectViewState
class ProfesorMateriaDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<ProfesorMateriaDetailsViewContract>(
    mCompositeDisposable,
    mDataManager
),
    ProfesorMateriaDetailsPresenterContract {

    override fun subscribe(idRelacion: Long, position: Int, model: ProfesorMateriaDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        val zip = Observable.zip(
            dataManager.allProfesores,
            dataManager.allMaterias,
            BiFunction { t1: MutableList<ProfesorDetailsDTO>, t2: MutableList<MateriaDetailsDTO> ->
                return@BiFunction Pair(t1, t2)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        if (idRelacion == 0L) {
            isInEditMode = false
            compositeDisposable.add(zip
                .subscribe(
                    { pair: Pair<MutableList<ProfesorDetailsDTO>, MutableList<MateriaDetailsDTO>> ->
                        initView(model, pair.first, pair.second)
                    },
                    { error -> onError(error) }
                )
            )
            return
        }

        mItemID = idRelacion
        mItemPosition = position
        compositeDisposable.add(zip
            .subscribe(
                { pair: Pair<MutableList<ProfesorDetailsDTO>, MutableList<MateriaDetailsDTO>> ->
                    initView(model, pair.first, pair.second)
                },
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
        compositeDisposable.add(dataManager.removeProfesorMateria(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(relacion: ProfesorMateriaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = ProfesorMateriaDTO(0, relacion.profesor.cedula, relacion.materia.codigo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.addProfesorMateria(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, relacion) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(relacion: ProfesorMateriaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = ProfesorMateriaDTO(mItemID, relacion.profesor.cedula, relacion.materia.codigo)

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateProfesorMateria(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, relacion) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(
        relacion: ProfesorMateriaDetailsDTO?,
        profesores: MutableList<ProfesorDetailsDTO>,
        materias: MutableList<MateriaDetailsDTO>
    ) {
        val collator = Collator.getInstance(Locale.US)
        profesores.sortWith(Comparator { c1, c2 -> collator.compare(c1.nombre, c2.nombre) })
        materias.sortBy { it.asignatura }
        viewState.setMateriasSpinnerItems(materias)
        viewState.setProfesoresSpinnerItems(profesores)
        viewState.enableAllViews(true)
        viewState.hideLoading()
        viewState.showItem(relacion)
    }
}