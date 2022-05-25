package com.n0lik.common.test.dispatcher

import com.n0lik.sample.common.dispatcher.AppDispatcher
import kotlinx.coroutines.Dispatchers

class TestAppDispatcher : AppDispatcher {

    override val IO = Dispatchers.Unconfined
    override val Main = Dispatchers.Unconfined
    override val Default = Dispatchers.Unconfined
    override val Unconfined = Dispatchers.Unconfined
}