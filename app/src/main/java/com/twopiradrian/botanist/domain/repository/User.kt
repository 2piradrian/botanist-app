package com.twopiradrian.botanist.domain.repository

import com.twopiradrian.botanist.domain.usecase.user.Login
import com.twopiradrian.botanist.domain.usecase.user.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.user.Register

interface UserRepository {
    suspend fun refreshTokens(request: RefreshTokens.Request): RefreshTokens.Response

    suspend fun login(request: Login.Request): Login.Response

    suspend fun register(request: Register.Request): Register.Response

}