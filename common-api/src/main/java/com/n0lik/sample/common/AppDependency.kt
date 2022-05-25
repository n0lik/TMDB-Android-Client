package com.n0lik.sample.common

import com.n0lik.sample.common.dispatcher.AppDispatcher
import io.ktor.client.HttpClient
import retrofit2.Retrofit

interface AppDependency {

    fun getAppDispatcher(): AppDispatcher

    fun getRetrofit(): Retrofit

    /**
     * Ktor HttpClient
     */
    fun getHttpClient(): HttpClient
}