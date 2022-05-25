package com.n0lik.sample.common.network

import okhttp3.Interceptor
import okhttp3.Response

internal class ContentTypeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()

        val newUrl = originalUrl.newBuilder().build()
        val newRequest = original.newBuilder()
            .url(newUrl)
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}

/**
 * @param apiKey - key from the api service
 */
internal class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}