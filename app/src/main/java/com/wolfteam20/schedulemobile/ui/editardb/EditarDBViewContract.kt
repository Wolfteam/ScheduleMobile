package com.wolfteam20.schedulemobile.ui.editardb

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
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
     * Inicia la detail activity pasandole el [itemID] del objeto a ser buscado en la db
     * y la [itemPosition] del item en la recycler view. Pasar estos parametros solo en caso de Editar
     * no son necesarios para Agregar
     */
    @StateStrategyType(SkipStrategy::class)
    fun startDetailsActivity(itemID: Long = 0, itemPosition: Int = 0)

    /**
     * Selecciona/Deselecciona un item en la [itemPosition] indicada
     */
    fun toggleItemSelection(itemPosition: Int)
}