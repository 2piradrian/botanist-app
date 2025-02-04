package com.twopiradrian.botanist.domain.usecase.post

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.PostRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import retrofit2.HttpException

class Delete {
    private val repository: PostRepository = PostRepository()

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

    suspend fun invoke(token: String, request: Request): Result {
        return try {
            val response = repository.delete(token, request)
            Result(response = Response(message = response.message))
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