package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import retrofit2.HttpException

class GetProfile {
    private val repository: UserRepository = UserRepository()

    data class Request(
        @SerializedName("profile") val profile: String,
        @SerializedName("includePosts") val includePosts: Boolean
    )

    data class Response(
        @SerializedName("user") val user: UserEntity,
        @SerializedName("posts") val posts: List<PostEntity>
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke (tokens: TokensEntity, request: Request): Result {
        return try {
            val response = repository.getProfile(tokens.accessToken, request)
            Result(response = Response(user = response.user, posts = response.posts))
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error"        -> Result(error = R.string.server_error)
                        else                    -> Result(error = R.string.server_error)
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