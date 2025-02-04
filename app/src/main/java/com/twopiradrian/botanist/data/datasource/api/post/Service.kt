package com.twopiradrian.botanist.data.datasource.api.post

import com.twopiradrian.botanist.core.helper.RetrofitHelper
import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.domain.usecase.post.Delete
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostAPIService: Client {
    private val retrofit = RetrofitHelper.getRetrofit()
    override suspend fun create(token: String, request: Create.Request): Create.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).create("Bearer $token", request)
        }
    }

    override suspend fun getByCategories(token: String, page: Int, pageSize: Int, categories: String): GetByCategories.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).getByCategories("Bearer $token", page, pageSize, categories)
        }
    }

    override suspend fun delete(token: String, postId: String): Delete.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).delete("Bearer $token", postId)
        }
    }
}