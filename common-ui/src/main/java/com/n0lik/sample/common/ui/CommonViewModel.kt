package com.n0lik.sample.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class CommonViewModel : ViewModel() {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        handleThrowable(coroutineContext, throwable)
    }

    override fun onCleared() {
        super.onCleared()
    }

    protected open fun handleThrowable(
        coroutineContext: CoroutineContext,
        throwable: Throwable
    ) {
        throwable.printStackTrace()
    }

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}