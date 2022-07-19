package com.n0lik.sample.common

import android.content.Context
import android.content.SharedPreferences
import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.domain.ConfigRepository
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

interface AppDependency {

    fun getContext(): Context

    fun getAppDispatcher(): AppDispatcher

    fun getRetrofit(): Retrofit

    /**
     * Ktor HttpClient
     */
    fun getHttpClient(): HttpClient

    fun getSharedPreferences(): SharedPreferences

    fun getJson(): Json

    fun getConfigRepository(): ConfigRepository
}