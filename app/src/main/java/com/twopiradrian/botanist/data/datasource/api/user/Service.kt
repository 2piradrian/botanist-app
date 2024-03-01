package com.twopiradrian.botanist.data.datasource.api.user

import com.twopiradrian.botanist.core.helper.RetrofitHelper
import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetFeed
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAPIService: Client {
    private val retrofit = RetrofitHelper.getRetrofit()

    override suspend fun getFeed(token: String, page: Int, pageSize: Int): GetFeed.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).getFeed("Bearer $token", page, pageSize)
        }
    }

    override suspend fun getProfile(token: String, request: GetProfile.Request): GetProfile.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).getProfile("Bearer $token", request)
        }
    }
    override suspend fun likePost(token: String, request: LikePost.Request): LikePost.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).likePost("Bearer $token", request)
        }
    }
    override suspend fun followUser(token: String, request: FollowUser.Request): FollowUser.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).followUser("Bearer $token", request)
        }
    }

}