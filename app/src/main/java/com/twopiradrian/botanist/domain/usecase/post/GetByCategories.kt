package com.twopiradrian.botanist.domain.usecase.post

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.PostRepository
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.domain.data.HTTPError
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity
import retrofit2.HttpException

class GetByCategories {
    private val repository: PostRepository = PostRepository()

    data class Request(
        @SerializedName("categories") val categories: List<Categories>,
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
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error" -> Result(error = R.string.server_error)
                        else             -> Result(error = R.string.server_error)
                    }
                }
                else {
                    Result(error = R.string.server_error)
                }

            }
            else {
                Result(error = R.string.server_error)
            }
        }
    }
}