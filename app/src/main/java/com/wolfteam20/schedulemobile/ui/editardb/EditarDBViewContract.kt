package com.wolfteam20.schedulemobile.ui.editardb

import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface EditarDBViewContract : BaseViewContract {
    fun showSwipeToRefresh()
    fun hideSwipeToRefresh()
//    fun showLoading()
//    fun hideLoading()
    fun showFAB()
    fun hideFAB()

    /**
     * Inicia la detail activity pasandole el id del objeto a ser buscado en la db
     */
    fun startDetailsActivity(id: Long)

    /**
     * Selecciona/Deselecciona un item en la [position] indicada
     */
    fun toggleItemSelection(position: Int)
}