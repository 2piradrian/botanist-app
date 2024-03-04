package com.twopiradrian.botanist.domain.repository

import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.domain.usecase.post.Delete
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories

interface PostRepository {
    suspend fun create(token: String, request: Create.Request): Create.Response
    suspend fun getByCategories(token: String, request: GetByCategories.Request): GetByCategories.Response

    suspend fun delete(token: String, request: Delete.Request): Delete.Response
}