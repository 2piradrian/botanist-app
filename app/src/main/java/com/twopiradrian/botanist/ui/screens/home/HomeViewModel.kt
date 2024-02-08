package com.twopiradrian.botanist.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _isShowingHomePage = MutableStateFlow(true)
    val isShowingHomePage: StateFlow<Boolean> = _isShowingHomePage

    fun setIsShowingHomePage(b: Boolean) {
        _isShowingHomePage.value = b
    }
}