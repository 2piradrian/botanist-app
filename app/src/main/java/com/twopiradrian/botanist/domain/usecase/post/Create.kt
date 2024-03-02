package com.twopiradrian.botanist.domain.usecase.post

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.repository.PostRepository
import com.twopiradrian.botanist.domain.data.HTTPError
import retrofit2.HttpException


class Create {
    private val repository: PostRepository = PostRepository()

    data class Request(
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("category") val category: String,
        @SerializedName("image") val image: String,
        @SerializedName("content") val content: String
    )

    data class Response(
        @SerializedName("id") val id: String
    )

    data class Result(
        val error: Int? = null,
        val response: Response? = null
    )

    suspend fun invoke(token: String, request: Request): Result {
        return try {
            val response = repository.create(token, request)
            Result(response = Response(id = response.id))
        }
        catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorJson = Gson().fromJson(errorResponse, HTTPError::class.java)

                if (errorJson != null) {
                    when (errorJson.error) {
                        "Internal error" -> Result(error = R.string.server_error)
                        "Invalid fields" -> Result(error = R.string.api_invalid_fields)
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