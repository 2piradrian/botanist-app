package com.twopiradrian.botanist.domain.entity

import java.util.Date

data class PostEntity(
    val id: String,
    val author: UserEntity,
    val createdAt: Date,
    val title: String,
    val description: String,
    val category: String,
    val image: String,
    val content: String,
)