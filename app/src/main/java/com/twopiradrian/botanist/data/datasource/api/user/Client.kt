package com.twopiradrian.botanist.data.datasource.api.user

import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface Client {

    @GET("user/get-profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
        @Query ("profile") profile: String,
        @Query ("includePosts") includePosts: Boolean
    ): GetProfile.Response


    @PUT("user/like-post")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Body request: LikePost.Request
    ): LikePost.Response

    @PUT("user/follow-user")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Body request: FollowUser.Request
    ): FollowUser.Response
}