package com.twopiradrian.botanist.data.datasource.api.post

import com.twopiradrian.botanist.core.helper.RetrofitHelper
import com.twopiradrian.botanist.domain.usecase.post.Create
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

    override suspend fun getByCategories(token: String, request: GetByCategories.Request): GetByCategories.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).getByCategories("Bearer $token", request)
        }
    }

}