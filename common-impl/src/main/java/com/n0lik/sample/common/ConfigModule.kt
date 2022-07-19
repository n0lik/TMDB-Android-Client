package com.n0lik.sample.common

import com.n0lik.sample.common.data.api.ConfigApi
import com.n0lik.sample.common.data.api.ConfigApiImpl
import com.n0lik.sample.common.domain.ConfigRepository
import com.n0lik.sample.common.domain.ConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface ConfigModule {

    @Binds
    @Singleton
    fun provideConfigApi(impl: ConfigApiImpl): ConfigApi

    @Binds
    @Singleton
    fun provideConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository
}