package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Parcelable
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
interface ItemBaseViewContract : BaseViewContract {
    fun showSwipeToRefresh()
    fun hideSwipeToRefresh()
    fun showFAB()
    fun hideFAB()

    /**
     * Inicia la detail activity en el [fragment] indicado pasandole el [itemID] del objeto,
     * la [itemPosition] en la recyclerview y el [item] que debe implementar la interfaz Parcelable.
     * Pasar estos parametros solo en caso de Editar no son necesarios para Agregar
     */
    @StateStrategyType(SkipStrategy::class)
    fun startDetailsActivity(
        fragment: Int,
        itemID: Long = 0,
        itemPosition: Int = 0,
        item: Parcelable? = null
    )


    /**
     * Selecciona/Deselecciona un item en la [itemPosition] indicada
     */
    fun toggleItemSelection(itemPosition: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeSelectedListItems()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startActionMode()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setActionModeTitle(title: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopActionMode()

    @StateStrategyType(SkipStrategy::class)
    fun showConfirmDelete()

    @StateStrategyType(SkipStrategy::class)
    fun showNoItemsSelected()

}