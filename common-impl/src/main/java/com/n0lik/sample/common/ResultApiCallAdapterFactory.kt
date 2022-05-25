package com.n0lik.sample.common

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResultApiCallAdapterFactory constructor(
    private val errorFactory: ErrorFactory
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    Result::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        ResultAdapter(resultType, errorFactory)
                    }
                    else -> null
                }
            }
            else -> null
        }
    }
}

sealed class Result<out T> {

    class Ok<T>(val value: T) : Result<T>()
    class Error(val throwable: Throwable) : Result<Nothing>()

    fun unwrap(): T {
        return when (this) {
            is Ok -> value
            is Error -> throw throwable
        }
    }
}

private class ResultAdapter(
    private val type: Type,
    private val errorFactory: ErrorFactory
) : CallAdapter<Type, Call<Result<Type>>> {

    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call, errorFactory)
}

private class ResultCall<T>(
    proxy: Call<T>,
    private val errorFactory: ErrorFactory
) : CallDelegate<T, Result<T>>(proxy) {

    override fun timeout(): Timeout = Timeout.NONE

    override fun cloneImpl() = ResultCall(proxy.clone(), errorFactory)

    override fun enqueueImpl(callback: Callback<Result<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T> = if (response.isSuccessful) {
                @Suppress("USELESS_CAST")
                // false positive
                Result.Ok(response.body()!!) as Result<T>
            } else {
                Result.Error(errorFactory.createFromErrorBody(response.errorBody()))
            }
            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(
                this@ResultCall,
                Response.success(Result.Error(errorFactory.createFromThrowable(t)))
            )
        }
    })
}

private abstract class CallDelegate<IN, OUT>(
    protected val proxy: Call<IN>
) : Call<OUT> {

    override fun clone(): Call<OUT> = cloneImpl()

    override fun execute(): Response<OUT> = throw NotImplementedError()

    override fun enqueue(callback: Callback<OUT>) = enqueueImpl(callback)

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() = proxy.cancel()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    abstract fun enqueueImpl(callback: Callback<OUT>)

    abstract fun cloneImpl(): Call<OUT>
}

interface ErrorFactory {

    fun createFromThrowable(t: Throwable): Throwable
    fun createFromErrorBody(body: ResponseBody?): Throwable
}