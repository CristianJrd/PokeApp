package com.example.pokedex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


typealias MutableLiveDataState<T> = MutableLiveData<StateApp<T>>
typealias LiveDataState<T> = LiveData<StateApp<T>>

sealed class StateApp<out T> {
    class Success<T>(val value: T) : StateApp<T>()
    class Error(val e: Throwable, val retry: () -> Unit = {}) : StateApp<Nothing>()

    fun fold(data: (T) -> Unit, error: (e: Throwable, retry: () -> Unit) -> Unit) {
        when (this) {
            is Success -> data(value)
            is Error -> error(e, retry)
        }
    }
}

val <T> StateApp<T>?.isSuccessful: Boolean
    get() {
        val stateNonNull = this ?: return false
        return stateNonNull is StateApp.Success
    }