package com.wolfteam20.schedulemobile.ui.editardb.materia.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.CarreraDTO
import com.wolfteam20.schedulemobile.data.network.models.SemestreDTO
import com.wolfteam20.schedulemobile.data.network.models.TipoAulaMateriaDTO
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseViewContract

/**
 * Created by Efrain.Bastidas on 17/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MateriaDetailsViewContract : ItemDetailsBaseViewContract {
    fun setCarreraSpinnerItems(carreras: MutableList<CarreraDTO>)
    fun setTipoMateriaSpinnerItems(tipos: MutableList<TipoAulaMateriaDTO>)
    fun setSemestresSpinnerItems(semestres: MutableList<SemestreDTO>)
}