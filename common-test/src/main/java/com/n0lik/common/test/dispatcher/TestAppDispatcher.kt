package com.n0lik.common.test.dispatcher

import com.n0lik.sample.common.dispatcher.AppDispatcher
import kotlinx.coroutines.Dispatchers

class TestAppDispatcher : AppDispatcher {

    override val io = Dispatchers.Unconfined
    override val main = Dispatchers.Unconfined
    override val default = Dispatchers.Unconfined
    override val unconfined = Dispatchers.Unconfined
}