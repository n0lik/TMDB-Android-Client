package com.n0lik.sample.common

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.dispatcher.AppDispatcherImpl
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {

    @Binds
    fun provideAppDispatcher(impl: AppDispatcherImpl): AppDispatcher
}