package com.twopiradrian.botanist.domain.usecase.user

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.UserRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import com.twopiradrian.botanist.domain.entity.TokensEntity
import retrofit2.HttpException

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
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error"        -> Result(error = R.string.server_error)
                        "Can't follow yourself" -> Result(error = R.string.api_cant_follow_yourself)
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