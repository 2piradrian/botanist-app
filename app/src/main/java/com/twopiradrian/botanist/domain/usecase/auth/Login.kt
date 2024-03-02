package com.twopiradrian.botanist.domain.usecase.auth

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.AuthRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import retrofit2.HttpException

class Login {
    private val repository: AuthRepository = AuthRepository()

    data class Request(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String
    )

    data class Response(
        @SerializedName("user") val user: UserEntity,
        @SerializedName("tokens") val tokens: TokensEntity,
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke(request: Request): Result {
        return try {
            val response = repository.login(request = request)
            Result(response = Response(user = response.user, tokens = response.tokens))
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error"            -> Result(error = R.string.server_error)
                        "User not found"            -> Result(error = R.string.api_user_not_found)
                        "Invalid password"          -> Result(error = R.string.api_invalid_password)
                        else                        -> Result(error = R.string.server_error)
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