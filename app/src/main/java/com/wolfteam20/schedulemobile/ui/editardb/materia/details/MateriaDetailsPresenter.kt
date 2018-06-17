package com.wolfteam20.schedulemobile.ui.editardb.materia.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.*
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */

@InjectViewState
class MateriaDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<MateriaDetailsViewContract>(mCompositeDisposable, mDataManager),
    MateriaDetailsPresenterContract {

    override fun subscribe(codigo: Long, position: Int, model: MateriaDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        if (codigo == 0L) {
            isInEditMode = false
            compositeDisposable.add(
                Single.zip(
                    dataManager.allCarreras,
                    dataManager.allTipoAulaMateria,
                    dataManager.allSemestres,
                    Function3<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>,
                            Triple<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>>>
                    { t1, t2, t3 -> return@Function3 Triple(t1, t2, t3) })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { triple: Triple<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>> ->
                            initView(model, triple.first, triple.second, triple.third)
                        },
                        { error -> onError(error) }
                    )
            )
            return
        }

        mItemID = codigo
        mItemPosition = position
        compositeDisposable.add(
            Single.zip(
                dataManager.allCarreras,
                dataManager.allTipoAulaMateria,
                dataManager.allSemestres,
                Function3<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>,
                        Triple<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>>>
                { t1, t2, t3 -> return@Function3 Triple(t1, t2, t3) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { triple: Triple<MutableList<CarreraDTO>, MutableList<TipoAulaMateriaDTO>, MutableList<SemestreDTO>> ->
                        initView(model, triple.first, triple.second, triple.third)
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
        compositeDisposable.add(dataManager.removeMateria(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(materia: MateriaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = MateriaDTO(
            materia.codigo,
            materia.asignatura,
            materia.horasAcademicasTotales,
            materia.horasAcademicasSemanales,
            materia.semestre.idSemestre,
            materia.tipoMateria.idTipo,
            materia.carrera.idCarrera
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.addMateria(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, materia) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(materia: MateriaDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = MateriaDTO(
            materia.codigo,
            materia.asignatura,
            materia.horasAcademicasTotales,
            materia.horasAcademicasSemanales,
            materia.semestre.idSemestre,
            materia.tipoMateria.idTipo,
            materia.carrera.idCarrera
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateMateria(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, materia) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(
        materia: MateriaDetailsDTO?,
        carreras: MutableList<CarreraDTO>,
        tipoAulaMateria : MutableList<TipoAulaMateriaDTO>,
        semestres: MutableList<SemestreDTO>
    ) {
        viewState.setCarreraSpinnerItems(carreras)
        viewState.setSemestresSpinnerItems(semestres)
        viewState.setTipoMateriaSpinnerItems(tipoAulaMateria)
        viewState.enableAllViews(true)
        viewState.hideLoading()
        viewState.showItem(materia)
    }
}