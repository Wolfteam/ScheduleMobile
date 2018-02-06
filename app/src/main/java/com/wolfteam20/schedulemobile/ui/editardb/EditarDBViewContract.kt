package com.wolfteam20.schedulemobile.ui.editardb

import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface EditarDBViewContract : BaseViewContract {
    fun showLoading()
    fun hideLoading()
    fun showFAB()
    fun hideFAB()
}