package com.twopiradrian.botanist.domain.usecase.auth

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.AuthRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import retrofit2.HttpException

class RefreshTokens {
    private val repository: AuthRepository = AuthRepository()

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

    suspend operator fun invoke(request: Request): Result {
        return try {
            val response = repository.refreshTokens(request = request)
            Result(response = Response(accessToken = response.accessToken, refreshToken = response.refreshToken))
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error" -> Result(error = R.string.server_error)
                        else             -> Result(error = R.string.server_error)
                    }
                } else {
                    Result(error = R.string.server_error)
                }

            }
            else {
                Result(error = R.string.server_error)
            }
        }
    }
}