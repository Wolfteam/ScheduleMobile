package com.wolfteam20.schedulemobile.data.db.Repository

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
interface RepositoryContract<TEntity> {
    /**
     * Agrega una [entity] de tipo [TEntity] a la db
     */
    fun add(entity: TEntity)

    /**
     * Agrega varias [entities] de tipo [TEntity]
     */
    fun addRange(entities: List<TEntity>)

    /**
     * Obtiene una entidad mediante su [id] de tipo [TEntity]
     */
    fun get(id: Long): Single<TEntity>

    /**
     * Obtiene todas las entidades de tipo [TEntity]
     */
    fun getAll(): Observable<List<TEntity>>

    /**
     * Remueve todas las entidades de tipo [TEntity]
     */
    fun remove()

    /**
     * Remueve una entidad de tipo [TEntity] mediante su [id]
     */
    fun remove(id: Long)

    /**
     * Remueve una entidad de tipo [TEntity]
     */
    fun remove(entity: TEntity)

    /**
     * Remueve todas las [entities] pasadas por parametro
     */
    fun removeRange(entities: List<TEntity>)

    /**
     * Actualiza una entidad primero borrando la indicada mediante [id]
     * y luego insertando la indicada en [entity]
     */
    fun update(id: Long, entity: TEntity)

    /**
     * Actualiza la [entity] pasada
     */
    fun update(entity: TEntity)
}