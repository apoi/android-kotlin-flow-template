package com.example.app.network.result

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<Result<Type>>> {

    override fun responseType() = type

    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultDelegate(call)
}
