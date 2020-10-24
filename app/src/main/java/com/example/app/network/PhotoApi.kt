package com.example.app.network

import com.example.app.domain.photo.Photo
import com.example.app.network.result.ApiResult
import retrofit2.http.GET

interface PhotoApi {

    @GET("/photos")
    suspend fun get(): ApiResult<List<Photo>>
}
