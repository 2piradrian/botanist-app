package com.twopiradrian.botanist.domain.repository

import com.twopiradrian.botanist.domain.usecase.auth.Login
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.auth.Register

interface AuthRepository {
    suspend fun refreshTokens(request: RefreshTokens.Request): RefreshTokens.Response

    suspend fun login(request: Login.Request): Login.Response

    suspend fun register(request: Register.Request): Register.Response

}