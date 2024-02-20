package com.twopiradrian.botanist.domain.repository

import com.twopiradrian.botanist.domain.usecase.post.Create

interface PostRepository {
    suspend fun create(token: String, request: Create.Request): Create.Response
}