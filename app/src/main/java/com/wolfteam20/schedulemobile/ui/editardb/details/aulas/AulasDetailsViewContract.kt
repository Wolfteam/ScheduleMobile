package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract
import com.wolfteam20.schedulemobile.ui.editardb.details.EditarDBDetailsViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AulasDetailsViewContract : EditarDBDetailsViewContract {
    fun showItem(aula: AulaDetailsDTO)

    //Este metodo quizas lo mueva a EditarDBDetailsViewContract
    @StateStrategyType(SkipStrategy::class)
    fun finishActivity(operation: Int, position: Int = 0)

    @StateStrategyType(SkipStrategy::class)
    fun prepareData(isInEditMode: Boolean)
}