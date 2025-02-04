package com.twopiradrian.botanist.data.repository

import com.twopiradrian.botanist.data.datasource.api.auth.AuthAPIService
import com.twopiradrian.botanist.domain.repository.AuthRepository
import com.twopiradrian.botanist.domain.usecase.auth.Login
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.auth.Register

class AuthRepository: AuthRepository {
    private val api = AuthAPIService()
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