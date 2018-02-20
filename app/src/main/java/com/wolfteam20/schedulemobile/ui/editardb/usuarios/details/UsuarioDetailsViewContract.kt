package com.wolfteam20.schedulemobile.ui.editardb.usuarios.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.PrivilegioDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface UsuarioDetailsViewContract : ItemDetailsBaseViewContract {
    fun showItem(usuario: UsuarioDetailsDTO)
    fun setProfesorSpinnerItems(profesores: MutableList<ProfesorDetailsDTO>)
    fun setPrivilegioSpinnerItems(prioridades: MutableList<PrivilegioDTO>)
}