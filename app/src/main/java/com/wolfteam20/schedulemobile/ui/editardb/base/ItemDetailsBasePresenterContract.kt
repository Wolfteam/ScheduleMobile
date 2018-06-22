package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Parcelable
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */

interface ItemDetailsBasePresenterContract : BasePresenterContract {
    fun onCancelClicked()
    fun onDeleteClicked()
    fun onSaveClicked()
}