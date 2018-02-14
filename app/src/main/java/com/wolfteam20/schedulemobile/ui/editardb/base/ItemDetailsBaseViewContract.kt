package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Parcelable
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ItemDetailsBaseViewContract : BaseViewContract {
    fun showLoading()

    fun hideLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showConfirmDeleteDialog()

    fun enableAllViews(enabled: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun finishActivity(operation: Int, position: Int = 0, item: Parcelable? = null)

    @StateStrategyType(SkipStrategy::class)
    fun prepareData(isInEditMode: Boolean)
}