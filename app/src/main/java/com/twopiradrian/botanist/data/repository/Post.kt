package com.twopiradrian.botanist.data.repository

import com.twopiradrian.botanist.data.datasource.api.post.PostAPIService
import com.twopiradrian.botanist.domain.repository.PostRepository
import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.domain.usecase.post.Delete
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories

class PostRepository: PostRepository {
    private val api = PostAPIService()
    override suspend fun create(token: String, request: Create.Request): Create.Response {
        return api.create(token, request)
    }

    override suspend fun getByCategories(token: String, request: GetByCategories.Request): GetByCategories.Response {
        val categoriesString = request.categories.joinToString(separator = ",") { it.name }

        return api.getByCategories(token, request.page, request.pageSize, categoriesString)
    }

    override suspend fun delete(token: String, request: Delete.Request): Delete.Response {
        return api.delete(token, request.postId)
    }

}