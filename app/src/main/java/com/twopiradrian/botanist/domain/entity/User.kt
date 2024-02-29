package com.twopiradrian.botanist.domain.entity

data class UserEntity(
    val id: String,
    val email: String,
    val username: String,
    val posts: MutableList<String>,
    val followers: MutableList<String> = mutableListOf(),
    val following: List<String> = mutableListOf(),
    val likes: MutableList<String> = mutableListOf(),
)

data class UserDataEntity (
    val id: String,
    val email: String,
)