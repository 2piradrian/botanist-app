package com.twopiradrian.botanist.data.repository

import com.twopiradrian.botanist.data.datasource.api.user.UserAPIService
import com.twopiradrian.botanist.domain.repository.UserRepository
import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost

class UserRepository: UserRepository {
    private val api = UserAPIService()

    override suspend fun getProfile(token: String, request: GetProfile.Request): GetProfile.Response {
        return api.getProfile(token, request.profile, request.includePosts)
    }

    override suspend fun likePost(token: String, request: LikePost.Request): LikePost.Response {
        return api.likePost(token, request)
    }

    override suspend fun followUser(token: String, request: FollowUser.Request): FollowUser.Response {
        return api.followUser(token, request)
    }
}