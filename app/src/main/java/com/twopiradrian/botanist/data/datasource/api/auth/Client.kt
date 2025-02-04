package com.twopiradrian.botanist.data.datasource.api.auth


import com.twopiradrian.botanist.domain.usecase.auth.Login
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.auth.Register
import retrofit2.http.Body
import retrofit2.http.POST

interface Client {
    @POST("auth/refresh-token")
    suspend fun refreshTokens(
        @Body request: RefreshTokens.Request
    ): RefreshTokens.Response

    @POST("auth/login")
    suspend fun login(
        @Body request: Login.Request
    ): Login.Response

    @POST("auth/register")
    suspend fun register(
        @Body request: Register.Request
    ): Register.Response

}