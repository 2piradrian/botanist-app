package com.twopiradrian.botanist.domain.usecase.post

import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.data.repository.PostRepository
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity

class GetByCategories {
    private val repository: PostRepository = PostRepository()

    data class Request(
        @SerializedName("categories") val categories: List<String>,
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
            val response = repository.getByCategories(tokens.accessToken, request)
            Result(response = Response(response.posts, response.nextPage))
        } catch (e: Exception) {
            e.printStackTrace()

            // TODO: Handle error
            Result(error = 0)
        }
    }
}