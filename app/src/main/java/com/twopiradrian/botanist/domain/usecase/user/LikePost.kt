package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.entity.TokensEntity


class LikePost {
    private val repository: UserRepository = UserRepository()

    data class Request(
        @SerializedName("postId") val postId: String
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
            val response = repository.likePost(tokens.accessToken, request)
            Result(response = Response(response.message))
        } catch (e: Exception) {
            e.printStackTrace()

            // TODO: Handle error
            Result(error = 0)
        }
    }

}