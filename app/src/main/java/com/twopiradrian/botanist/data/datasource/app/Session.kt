package com.twopiradrian.botanist.data.datasource.app

import android.content.Context
import android.content.SharedPreferences
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserDataEntity

object Session {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
    }

    fun saveUser(user: UserDataEntity) {
        sharedPreferences.edit().putString("USER_ID", user.id).apply()
        sharedPreferences.edit().putString("USER_EMAIL", user.email).apply()
    }

    fun saveTokens(tokens: TokensEntity) {
        sharedPreferences.edit().putString("ACCESS_TOKEN", tokens.accessToken).apply()
        sharedPreferences.edit().putString("REFRESH_TOKEN", tokens.refreshToken).apply()
    }

    fun getUser(): UserDataEntity {
        val id = sharedPreferences.getString("USER_ID", "") ?: ""
        val email = sharedPreferences.getString("USER_EMAIL", "") ?: ""

        return UserDataEntity(id, email)
    }

    fun getTokens(): TokensEntity {
        val accessToken = sharedPreferences.getString("ACCESS_TOKEN", "") ?: ""
        val refreshToken = sharedPreferences.getString("REFRESH_TOKEN", "") ?: ""

        return TokensEntity(accessToken, refreshToken)
    }
}