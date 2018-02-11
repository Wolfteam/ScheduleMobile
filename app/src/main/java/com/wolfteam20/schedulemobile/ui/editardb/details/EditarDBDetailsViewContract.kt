package com.wolfteam20.schedulemobile.ui.editardb.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface EditarDBDetailsViewContract : BaseViewContract {
    fun showLoading()

    fun hideLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showConfirmDeleteDialog()
}