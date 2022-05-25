package com.n0lik.common.test.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.respondRedirect
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
class MockKtorHttpClient
private constructor(
    private var mocks: List<MockResponse>?,
    private val headers: Headers = headersOf()
) {

    fun get(): HttpClient {
        return client
    }

    /**
     * Replace all mocks on desired
     * @param mock - any single mock for specific response
     */
    fun mockResponse(mock: MockResponse) {
        this.mocks = listOf(mock)
    }

    fun addMockResponses(mocks: List<MockResponse>) {
        this.mocks = mocks
    }

    fun mockErrorResponse(path: String, throwable: Throwable) {
        mockResponse(MockErrorThrowable(path, throwable))
    }

    fun mockOkResponse(path: String, responseJson: String) {
        mockResponse(MockOkFromJson(path, responseJson))
    }

    fun clearMocks() {
        this.mocks = null
    }

    private val client by lazy(LazyThreadSafetyMode.NONE) {
        HttpClient(MockEngine) {
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
                addHandler {
                    mockCall(it)
                }
            }
        }
    }

    private fun HttpRequestData.findMockByHttpRequestData(): MockResponse? {
        return mocks?.find { it.path == this.url.encodedPath }
    }

    private fun MockRequestHandleScope.mockCall(request: HttpRequestData): HttpResponseData {
        return when (val mock = request.findMockByHttpRequestData()) {
            is MockErrorThrowable -> throw mock.throwable
            is MockOkFromJson -> respond(mock.responseJson, HttpStatusCode.OK, headers)
            is MockBadRequest -> respondBadRequest()
            is MockRedirect -> respondRedirect(mock.location)
            is MockManualResponse -> respond(mock.responseJson, mock.status, mock.headers)
            else -> error(
                "Unhandled path { ${request.url.encodedPath} }!!!\n" +
                    "use mockResponses() or mockResponse() or mockOk() or mockError()\n" +
                    "or read documentation before using!!!"
            )
        }
    }

    companion object {

        fun builder(init: Builder.() -> Unit): MockKtorHttpClient {
            val builder = Builder()
            init.invoke(builder)
            return builder.build()
        }
    }

    class Builder {

        private var headers: Headers = headersOf()

        private var mocks: List<MockResponse>? = null

        fun mocks(mocks: List<MockResponse>) = apply { this.mocks = mocks }

        fun mocks(init: Builder.() -> List<MockResponse>) = mocks(init())

        fun headers(init: Builder.() -> Headers) = headers(init())

        fun headers(headers: Headers) = apply { this.headers = headers }

        fun build(): MockKtorHttpClient = MockKtorHttpClient(mocks, headers)

        companion object {

            val CONTENT_TYPE_JSON = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
        }
    }
}

sealed class MockResponse(val path: String)
class MockErrorThrowable(path: String, val throwable: Throwable) : MockResponse(path)
class MockOkFromJson(path: String, val responseJson: String) : MockResponse(path)
class MockBadRequest(path: String) : MockResponse(path)
class MockRedirect(path: String, val location: String) : MockResponse(path)
class MockManualResponse(
    path: String,
    val responseJson: String,
    val status: HttpStatusCode,
    val headers: Headers = headersOf()
) : MockResponse(path)