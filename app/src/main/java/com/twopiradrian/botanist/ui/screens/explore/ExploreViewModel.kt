package com.twopiradrian.botanist.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    private var page: Int? = 1
    private val pageSize: Int = 10

    private val _posts = MutableStateFlow(emptyList<PostEntity>())
    val posts: StateFlow<List<PostEntity>> = _posts

    suspend fun getPosts(
        session: Session,
        postList: List<PostEntity> = emptyList()
    ){
        viewModelScope.launch {
            if (page == null) return@launch

            val result = try {
                val request = GetByCategories.Request(
                    categories = listOf(),
                    page = page!!,
                    pageSize = pageSize
                )
                val tokens = session.getTokens()

                GetByCategories().invoke(tokens, request)
            }catch (e: Exception){
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _posts.value = postList + it.posts
            }

            result?.error?.let {
                page = null
            }


        }
    }


}