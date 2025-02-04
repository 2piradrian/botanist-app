package com.twopiradrian.botanist.domain.usecase.auth

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.AuthRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import retrofit2.HttpException

class Register {
    private val repository: AuthRepository = AuthRepository()

    data class Request(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("username") val username: String,
    )

    data class Response(
        @SerializedName("message") val message: String,
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke(request: Request): Result {
        return try {
            val response = repository.register(request = request)
            Result(response = Response(message = response.message))
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error"            -> Result(error = R.string.server_error)
                        "Username already exists"   -> Result(error = R.string.api_username_already_exists)
                        "Email already exists"      -> Result(error = R.string.api_email_already_exists)
                        else                        -> Result(error = R.string.server_error)
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