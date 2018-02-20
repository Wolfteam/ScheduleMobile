package com.wolfteam20.schedulemobile.ui.editardb.usuarios.details

import com.arellomobile.mvp.InjectViewState
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.data.DataManagerContract
import com.wolfteam20.schedulemobile.data.network.models.*
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBasePresenter
import io.reactivex.Single
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
class UsuarioDetailsPresenter @Inject constructor(
    mCompositeDisposable: CompositeDisposable,
    mDataManager: DataManagerContract
) : ItemDetailsBasePresenter<UsuarioDetailsViewContract>(mCompositeDisposable, mDataManager),
    UsuarioDetailsPresenterContract {

    override fun subscribe(cedula: Long, position: Int, model: UsuarioDetailsDTO?) {
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        viewState.enableAllViews(false)
        viewState.showLoading()

        val zip = Single.zip(
            dataManager.allProfesores.singleOrError(),
            dataManager.allPrivilegios,
            BiFunction { t1: MutableList<ProfesorDetailsDTO>, t2: MutableList<PrivilegioDTO> ->
                return@BiFunction Pair(t1, t2)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        if (cedula == 0L) {
            isInEditMode = false
            compositeDisposable.add(zip
                .subscribe(
                    { pair -> initView(pair.first, pair.second) },
                    { error -> onError(error) }
                )
            )
            return
        }

        mItemID = cedula
        mItemPosition = position
        compositeDisposable.add(zip
            .subscribe(
                { pair ->
                    initView(pair.first, pair.second)
                    viewState.showItem(model!!)
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
        compositeDisposable.add(dataManager.removeUsuario(mItemID)
            .subscribe(
                { viewState.finishActivity(DELETE_OPERATION, mItemPosition) },
                { error -> onError(error) }
            )
        )
    }

    override fun add(usuario: UsuarioDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = UsuarioDTO(
            usuario.cedula,
            usuario.username,
            usuario.password,
            usuario.privilegios.idPrivilegio
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.addUsuario(dto)
            .subscribe(
                { viewState.finishActivity(ADD_OPERATION, mItemPosition, usuario) },
                { error -> onError(error) }
            )
        )
    }

    override fun update(usuario: UsuarioDetailsDTO) {
        viewState.hideKeyboard()
        if (!isNetworkAvailable) {
            viewState.onError(R.string.no_network)
            return
        }

        val dto = UsuarioDTO(
            usuario.cedula,
            usuario.username,
            usuario.password,
            usuario.privilegios.idPrivilegio
        )

        viewState.showLoading()
        compositeDisposable.add(dataManager.updateUsuario(mItemID, dto)
            .subscribe(
                { viewState.finishActivity(UPDATE_OPERATION, mItemPosition, usuario) },
                { error -> onError(error) }
            )
        )
    }

    private fun initView(
        profesores: MutableList<ProfesorDetailsDTO>,
        privilegios: MutableList<PrivilegioDTO>
    ) {
        val collator = Collator.getInstance(Locale.US)
        profesores.sortWith(Comparator { c1, c2 -> collator.compare(c1.nombre, c2.nombre) })
        viewState.enableAllViews(true)
        viewState.setProfesorSpinnerItems(profesores)
        viewState.setPrivilegioSpinnerItems(privilegios)
        viewState.hideLoading()
    }
}