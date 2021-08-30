package com.example.pokedex.data.repository

import com.example.pokedex.data.database.Database
import io.realm.RealmObject
import io.realm.RealmQuery
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

abstract class PokemonDao<T : RealmObject> {

    abstract val modelClass: Class<T>

    inline fun <reified T : RealmObject> createObject(): T {
        return Database.getInstance().createObject(T::class.java, UUID.randomUUID().toString())
    }

    suspend fun createOrUpdate(realmObject: T) = suspendCancellableCoroutine<Unit> { cancellable ->
        try {
            Database.getInstance().executeTransaction {
                it.copyToRealmOrUpdate(realmObject)
                cancellable.resume(Unit)
            }
        } catch (e: Exception) {
            cancellable.resumeWithException(e)
        }
    }

    suspend fun createOrUpdateAll(listRealmObject: List<T>) =
        suspendCancellableCoroutine<Unit> { cancellable ->
            try {
                Database.getInstance().executeTransaction {
                    it.copyToRealmOrUpdate(listRealmObject)
                    cancellable.resume(Unit)
                }
            } catch (e: Exception) {
                cancellable.resumeWithException(e)
            }
        }

    suspend fun updateBy(query: RealmQuery<T>.() -> Unit, update: T.() -> Unit) =
        suspendCancellableCoroutine<Unit> { cancellable ->
            try {
                Database.getInstance().executeTransaction {
                    val realmObject = it.where(modelClass).apply(query).findFirst()
                    if (realmObject != null) {
                        it.copyToRealmOrUpdate(realmObject.apply(update))
                    }
                    cancellable.resume(Unit)
                }
            } catch (e: Exception) {
                cancellable.resumeWithException(e)
            }
        }

    suspend fun findFirst(): T? = findFirstBy { /* Empty implementation */ }

    suspend fun findFirstBy(query: RealmQuery<T>.() -> Unit): T? =
        suspendCancellableCoroutine { cancellable ->
            try {
                Database.getInstance().executeTransaction {
                    val realmObject = it.where(modelClass).apply(query).findFirst()
                    cancellable.resume(realmObject)
                }
            } catch (e: Exception) {
                cancellable.resumeWithException(e)
            }
        }

    suspend fun findAll(): List<T> = findAllBy { /* Empty implementation */ }

    suspend fun findAllBy(query: RealmQuery<T>.() -> Unit): List<T> =
        suspendCancellableCoroutine { cancellable ->
            try {
                Database.getInstance().executeTransaction {
                    val realmObjectList = it.where(modelClass).apply(query).findAll()
                    cancellable.resume(realmObjectList)
                }
            } catch (e: Exception) {
                cancellable.resumeWithException(e)
            }
        }

    suspend fun delete(realmObject: T) = suspendCancellableCoroutine<Unit> { cancellable ->
        try {
            Database.getInstance().executeTransaction {
                it.where(modelClass).findFirst()?.deleteFromRealm()
                cancellable.resume(Unit)
            }
        } catch (e: Exception) {
            cancellable.resumeWithException(e)
        }
    }

    suspend fun deleteAll() = suspendCancellableCoroutine<Unit> { cancellable ->
        try {
            Database.getInstance().executeTransaction {
                it.where(modelClass).findAll()?.deleteAllFromRealm()
                cancellable.resume(Unit)
            }
        } catch (e: Exception) {
            cancellable.resumeWithException(e)
        }
    }

}