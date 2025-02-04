package com.twopiradrian.botanist.data.datasource.api.post

import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.domain.usecase.post.Delete
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Client {
    @POST("posts/create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: Create.Request
    ): Create.Response

    @GET("posts/get-by-categories")
    suspend fun getByCategories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("categories") categories: String
    ): GetByCategories.Response

    @DELETE("posts/delete")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Query("postId") postId: String
    ): Delete.Response
}