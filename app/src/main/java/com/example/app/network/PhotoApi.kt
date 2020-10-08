package com.example.app.network

import com.example.app.data.model.Photo
import com.example.app.network.result.Result
import retrofit2.http.GET

interface PhotoApi {

    @GET("/photos")
    suspend fun get(): Result<List<Photo>>
}
