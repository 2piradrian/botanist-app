package com.twopiradrian.botanist.domain.repository

import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost

interface UserRepository {

    suspend fun getProfile(token: String, request: GetProfile.Request): GetProfile.Response
    suspend fun likePost(token: String, request: LikePost.Request): LikePost.Response
    suspend fun followUser(token: String, request: FollowUser.Request): FollowUser.Response
}