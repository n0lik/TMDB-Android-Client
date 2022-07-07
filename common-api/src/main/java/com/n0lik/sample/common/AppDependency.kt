package com.n0lik.sample.common

import android.content.Context
import com.n0lik.sample.common.dispatcher.AppDispatcher
import io.ktor.client.HttpClient
import retrofit2.Retrofit

interface AppDependency {

    fun getContext(): Context

    fun getAppDispatcher(): AppDispatcher

    fun getRetrofit(): Retrofit

    /**
     * Ktor HttpClient
     */
    fun getHttpClient(): HttpClient
}