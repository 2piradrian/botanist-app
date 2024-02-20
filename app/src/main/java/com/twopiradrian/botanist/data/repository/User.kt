package com.twopiradrian.botanist.data.repository

import com.twopiradrian.botanist.data.datasource.api.user.UserAPIService
import com.twopiradrian.botanist.domain.repository.UserRepository
import com.twopiradrian.botanist.domain.usecase.user.Login
import com.twopiradrian.botanist.domain.usecase.user.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.user.Register

class UserRepository: UserRepository {
    private val api = UserAPIService()
    override suspend fun refreshTokens(request: RefreshTokens.Request): RefreshTokens.Response {
       return api.refreshTokens(request)
    }

    override suspend fun login(request: Login.Request): Login.Response {
        return api.login(request)
    }

    override suspend fun register(request: Register.Request): Register.Response {
        return api.register(request)
    }
}