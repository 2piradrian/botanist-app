package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity

class GetFeed {
    val repository: UserRepository = UserRepository()
    data class Request(
        @SerializedName("page") val page: Int,
        @SerializedName("pageSize") val pageSize: Int
    )

    data class Response(
        @SerializedName("posts") val posts: List<PostEntity>,
        @SerializedName("nextPage") val nextPage: Int?
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke(tokens: TokensEntity, request: Request): Result {
        return try {
            val response = repository.getFeed(tokens.accessToken, request)
            Result(response = response)
        } catch (e: Exception) {
            e.printStackTrace()

            // TODO: Handle error
            Result(error = 0)
        }
    }
}