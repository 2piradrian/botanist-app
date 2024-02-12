package com.twopiradrian.botanist.domain.entity

data class UserEntity (
    val id: String,
    val email: String,
    val username: String,
    val posts: MutableList<String>,
)

data class UserDataEntity (
    val id: String,
    val email: String,
)