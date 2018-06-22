package com.wolfteam20.schedulemobile.data.db.Repository

import io.objectbox.Box
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
open class Repository<TEntity>(box: Box<TEntity>) :
    RepositoryContract<TEntity> {

    protected val mBox = box

    override fun add(entity: TEntity) {
        mBox.put(entity)
    }

    override fun addRange(entities: List<TEntity>) {
        mBox.put(entities)
    }

    override fun get(id: Long): Single<TEntity> {
        return Single.create { subscriber ->
            try {
                val entity = mBox.get(id)
                if (entity != null)
                    subscriber.onSuccess(entity)
                else {
                    val error = String.format("No se encontro la entidad con id: %s", id)
                    subscriber.onError(Throwable(error))
                }
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
            }
        }
    }

    override fun getAll(): Observable<List<TEntity>> {
        return Observable.create { subscriber ->
            try {
                val entity = mBox.query().build().find()
                subscriber.onNext(entity)
                subscriber.onComplete()
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
            }
        }
    }

    override fun remove() {
        mBox.query().build().remove()
    }

    override fun remove(id: Long) {
        mBox.remove(id)
    }

    override fun remove(entity: TEntity) {
        mBox.remove(entity)
    }

    override fun removeRange(entities: List<TEntity>) {
        mBox.remove(entities)
    }

    override fun update(id: Long, entity: TEntity) {
        remove(id)
        mBox.put(entity)
    }

    override fun update(entity: TEntity) {
        mBox.put(entity)
    }
}