package com.n0lik.sample.common

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.n0lik.sample.common.impl.BuildConfig
import com.n0lik.sample.common.network.ApiKeyInterceptor
import com.n0lik.sample.common.network.ContentTypeInterceptor
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

private const val DEFAULT_TIME_OUT = 30L

/**
 * Module contains Retrofit's settings up
 * Read official documentation there
 * @see <a href="https://square.github.io/retrofit/">Retrofit</a>
 */
@Module
internal class RetrofitNetworkModule {

    @Provides
    @IntoSet
    @NetworkInterceptor
    fun provideApiKeyInterceptor(): Interceptor {
        return ApiKeyInterceptor(apiKey = BuildConfig.API_KEY)
    }

    @Provides
    @IntoSet
    @NetworkInterceptor
    fun provideContentTypeInterceptor(): Interceptor {
        return ContentTypeInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @NetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)

        networkInterceptors.forEach { builder.addNetworkInterceptor(it) }

        return builder.build()
    }

    @Singleton
    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideConvertorFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            prettyPrint = true
        }
        return json.asConverterFactory(MediaType.get("application/json"))
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(converterFactory)
            .build()
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Qualifier
annotation class NetworkInterceptor