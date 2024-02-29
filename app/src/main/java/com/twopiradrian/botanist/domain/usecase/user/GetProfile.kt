package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserEntity

class GetProfile {
    private val repository: UserRepository = UserRepository()

    data class Request(
        @SerializedName("profile") val profile: String
    )

    data class Response(
        @SerializedName("user") val user: UserEntity
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke (tokens: TokensEntity, request: Request): Result {
        return try {
            val response = repository.getProfile(tokens.accessToken, request)
            Result(response = Response(user = response.user))
        } catch (e: Exception) {
            e.printStackTrace()

            // TODO: Handle error
            Result(error = 0)
        }
    }
}