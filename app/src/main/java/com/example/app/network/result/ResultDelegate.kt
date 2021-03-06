package com.example.app.network.result

import com.example.app.network.CallDelegate
import java.io.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultDelegate<T>(proxy: Call<T>) : CallDelegate<T, ApiResult<T>>(proxy) {

    override fun delegatedEnqueue(callback: Callback<ApiResult<T>>) = proxy.enqueue(
        object : Callback<T> {

            @Suppress("MagicNumber")
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = when (val code = response.code()) {
                    in 200 until 300 -> ApiResult.Success(response.body())
                    else -> ApiResult.HttpError(code, response.message())
                }

                callback.onResponse(this@ResultDelegate, Response.success(result))
            }

            override fun onFailure(call: Call<T>, error: Throwable) {
                val message = error.message
                    ?: error.cause?.message
                    ?: error.javaClass.simpleName

                val result = when (error) {
                    is HttpException -> ApiResult.HttpError(error.code(), message)
                    is IOException -> ApiResult.NetworkError(message)
                    else -> ApiResult.UnknownError(message)
                }

                callback.onResponse(this@ResultDelegate, Response.success(result))
            }
        }
    )

    override fun delegatedClone() = ResultDelegate(proxy.clone())
}
