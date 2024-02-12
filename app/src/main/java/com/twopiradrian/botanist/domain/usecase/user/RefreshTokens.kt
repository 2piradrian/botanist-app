package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.UserRepository

class RefreshTokens {
    private val repository: UserRepository = UserRepository()

    data class Request(
        @SerializedName("refreshToken") val refreshToken: String
    )

    data class Response(
        @SerializedName("accessToken") val accessToken: String,
        @SerializedName("refreshToken") val refreshToken: String,
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )
}