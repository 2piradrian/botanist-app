package com.twopiradrian.botanist.ui.app

import android.net.Uri
import androidx.annotation.StringRes

data class ImageData(
    val state: Uri?,
    val isError: Boolean,
    @StringRes val errorState: Int
) {
    companion object {
        fun empty() = ImageData(null, false, 0)
    }
}