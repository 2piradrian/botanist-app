package com.twopiradrian.botanist.data.datasource.api.post

import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Client {
    @POST("posts/create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: Create.Request
    ): Create.Response

    @POST("posts/get-by-categories")
    suspend fun getByCategories(
        @Header("Authorization") token: String,
        @Body request: GetByCategories.Request
    ): GetByCategories.Response
}