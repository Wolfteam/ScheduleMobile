package com.wolfteam20.schedulemobile.ui.adapters

/**
 * Created by Efrain.Bastidas on 14/2/2018.
 */
interface BaseItemListAdapterContract<TItem> {
    /**
     * Agrega un [item] y notifica al adapter
     */
    fun addItem(item: TItem)

    /**
     * Obtiene un item en la [itemPosition] indicada
     */
    fun getItem(itemPosition: Int): TItem

    /**
     * Obtiene una lista de disponibilidades en las posiciones indicadas
     * por [itemsPosition]
     */
    fun getItems(itemsPosition: ArrayList<Int>): MutableList<TItem>

    /**
     * Setea los [items] en la lista y notifica al adapter
     */
    fun setItems(items: MutableList<TItem>)

    /**
     * Actualiza un [item] en la [itemsPosition] indicada y notifica al adapter
     */
    fun updateItem(itemsPosition: Int, item: TItem)

    /**
     * Remueve un item de la lista en la [itemsPosition] indicada y
     * notifica  de los cambios al adapter
     */
    fun removeItem(itemsPosition: Int)

    /**
     * Remueve una o varios items en las [positions] indicadas
     * y limpia los elementos seleccionados
     */
    fun removeItems(positions: ArrayList<Int>)

    /**
     * Remueve un rango de items partiendo de [positionStart] hasta [itemCount]
     * y notifica de los cambios al adapter
     */
    fun removeRange(positionStart: Int, itemCount: Int)
}