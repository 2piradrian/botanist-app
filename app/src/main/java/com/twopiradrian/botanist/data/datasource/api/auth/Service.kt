package com.twopiradrian.botanist.data.datasource.api.auth

import com.twopiradrian.botanist.core.helper.RetrofitHelper
import com.twopiradrian.botanist.domain.usecase.auth.Login
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.auth.Register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthAPIService: Client {
    private val retrofit = RetrofitHelper.getRetrofit()

    override suspend fun refreshTokens(request: RefreshTokens.Request): RefreshTokens.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).refreshTokens(request)
        }
    }

    override suspend fun login(request: Login.Request): Login.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).login(request)
        }
    }

    override suspend fun register(request: Register.Request): Register.Response {
        return withContext(Dispatchers.IO) {
            retrofit.create(Client::class.java).register(request)
        }
    }


}