package com.twopiradrian.botanist.data.datasource.api.post

import com.twopiradrian.botanist.domain.usecase.post.Create
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Client {
    @POST("posts/create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: Create.Request
    ): Create.Response
}