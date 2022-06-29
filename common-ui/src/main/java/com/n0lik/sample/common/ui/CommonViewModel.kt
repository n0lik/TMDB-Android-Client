package com.n0lik.sample.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class CommonViewModel : ViewModel() {

    protected val errorHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    protected inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch(errorHandler) {
            val result = execute()
            onSuccess(result)
        }
    }

    protected open fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}