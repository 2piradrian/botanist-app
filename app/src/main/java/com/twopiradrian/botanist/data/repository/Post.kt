package com.twopiradrian.botanist.data.repository

import com.twopiradrian.botanist.data.datasource.api.post.PostAPIService
import com.twopiradrian.botanist.domain.repository.PostRepository
import com.twopiradrian.botanist.domain.usecase.post.Create

class PostRepository: PostRepository {
    private val api = PostAPIService()
    override suspend fun create(token: String, request: Create.Request): Create.Response {
        return api.create(token, request)
    }

}