package com.n0lik.sample.common

import android.util.Log
import com.n0lik.sample.common.impl.BuildConfig
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpSendPipeline
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Module contains Ktor's settings up
 * @see <a href="https://ktor.io/">Ktor</a>
 */
private const val DEFAULT_TIME_OUT = 20_000

@Module
@ExperimentalSerializationApi
internal class KtorNetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url(BuildConfig.API_URL)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
            engine {
                connectTimeout = DEFAULT_TIME_OUT
                socketTimeout = DEFAULT_TIME_OUT
            }
            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
        }.apply {
            /**
             * It intercepts request and adds "api_key" for authorization
             */
            sendPipeline.intercept(HttpSendPipeline.State) {
                with(context.url) {
                    trailingQuery = true
                    parameters.append("api_key", BuildConfig.API_KEY)
                }
            }
        }
    }
}