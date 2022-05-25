package com.n0lik.sample.common.dispatcher

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class AppDispatcherImpl
@Inject constructor() : AppDispatcher {

    override val IO: CoroutineDispatcher = Dispatchers.IO
    override val Main: CoroutineDispatcher = Dispatchers.Main
    override val Default: CoroutineDispatcher = Dispatchers.Default
    override val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}