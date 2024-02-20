package com.twopiradrian.botanist.data.datasource.api.user


import com.twopiradrian.botanist.domain.usecase.user.Login
import com.twopiradrian.botanist.domain.usecase.user.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.user.Register
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