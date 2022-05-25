package com.n0lik.sample.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatcher {

    val IO: CoroutineDispatcher

    val Main: CoroutineDispatcher

    val Default: CoroutineDispatcher

    val Unconfined: CoroutineDispatcher
}