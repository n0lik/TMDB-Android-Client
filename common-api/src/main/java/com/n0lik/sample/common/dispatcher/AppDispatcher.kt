package com.n0lik.sample.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatcher {

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher

    val default: CoroutineDispatcher

    val unconfined: CoroutineDispatcher
}