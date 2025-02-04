package com.twopiradrian.botanist.ui.app

import androidx.annotation.StringRes

data class InputData(
    val state: String,
    val isError: Boolean,
    @StringRes val errorState: Int
) {
    companion object {
        fun empty() = InputData("", false, 0)
    }
}

enum class InputType {
    EMAIL, TEXT, PASSWORD
}