package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.entity.TokensEntity

class FollowUser {
    private val repository: UserRepository = UserRepository()

    data class Request(
        @SerializedName("followedId") val followedId: String
    )

    data class Response(
        @SerializedName("message") val message: String
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke (tokens: TokensEntity, request: Request): Result {
        return try {
            val response = repository.followUser(tokens.accessToken, request)
            Result(response = Response(response.message))
        } catch (e: Exception) {
            e.printStackTrace()

            // TODO: Handle error
            Result(error = 0)
        }
    }


}