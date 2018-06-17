package com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 18/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfesorMateriaDetailsViewContract : ItemDetailsBaseViewContract {
    fun setProfesoresSpinnerItems(profesores: MutableList<ProfesorDetailsDTO>)
    fun setMateriasSpinnerItems(materias: MutableList<MateriaDetailsDTO>)
}