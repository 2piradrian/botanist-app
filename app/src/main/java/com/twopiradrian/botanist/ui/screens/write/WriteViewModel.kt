package com.twopiradrian.botanist.ui.screens.write

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.usecase.post.Create
import com.twopiradrian.botanist.ui.app.ImageData
import com.twopiradrian.botanist.ui.app.InputData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt

class WriteViewModel: ViewModel() {

    private val _isShowingThePost = MutableStateFlow(false)
    val isShowingThePost: MutableStateFlow<Boolean> = _isShowingThePost

    private val _title = MutableStateFlow(InputData.empty())
    val title: MutableStateFlow<InputData> = _title

    private val _description = MutableStateFlow(InputData.empty())
    val description: MutableStateFlow<InputData> = _description

    private val _category = MutableStateFlow(InputData.empty())
    val category: MutableStateFlow<InputData> = _category

    private val _image = MutableStateFlow(ImageData.empty())
    val image: MutableStateFlow<ImageData> = _image

    private val _content = MutableStateFlow(InputData.empty())
    val content: MutableStateFlow<InputData> = _content

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: MutableStateFlow<Boolean> = _isButtonEnabled

    private val _postedSuccessfully = MutableStateFlow(false)
    val postedSuccessfully: MutableStateFlow<Boolean> = _postedSuccessfully

    private val _error = MutableStateFlow(0)
    val error: MutableStateFlow<Int> = _error

    fun changeErrorState() {
        _error.value = 0
    }

    fun setIsShowingThePost(b: Boolean) {
        _isShowingThePost.value = b
    }

    fun onPostChange(
        title: String,
        description: String,
        category: String,
        image: Uri?,
        content: String
    ) {
        _title.update {
            it.copy(state = title, isError = !isTitleValid(title))
        }
        _description.update {
            it.copy(state = description, isError = !isDescriptionValid(description))
        }
        _category.update {
            it.copy(state = category, isError = !isCategoryValid(category))
        }
        _content.update {
            it.copy(state = content, isError = !isContentValid(content))
        }
        _isButtonEnabled.value = enablePostButton(title, description, category, image, content)
    }

    fun createPost(
        title: String,
        description: String,
        category: String,
        content: String,
        image: Uri?,
        context: Context,
        tokens: TokensEntity
    ) {
        viewModelScope.launch {
            val base64Image = image?.let { convertUriToBase64(it, context) } ?: ""

            val result = try {
                val request = Create.Request(title, description, category, base64Image, content)
                Create().invoke(tokens.accessToken, request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _postedSuccessfully.value = true
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                return@launch
            }

            _error.value = R.string.server_error
        }
    }

    fun onImageChange(uri: Uri?) {
        _image.update {
            it.copy(state = uri, isError = (uri == null))
        }
    }

    private suspend fun convertUriToBase64(uri: Uri, context: Context): String? {
        return withContext(Dispatchers.IO){
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val compressedBitmap = compressBitmap(bitmap)

            val byteArrayOutputStream = ByteArrayOutputStream()
            compressedBitmap.compress(Bitmap.CompressFormat.WEBP, 95, byteArrayOutputStream)

            val byteArray = byteArrayOutputStream.toByteArray()

            inputStream?.close()

            return@withContext Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }

    private fun compressBitmap(source: Bitmap): Bitmap {
        val maxSize = 1024 * 180

        var scale = sqrt((maxSize).toDouble() / (source.width * source.height))
        if (scale > 1) {
            scale = 1.0
        }
        val width = (source.width * scale).toInt()
        val height = (source.height * scale).toInt()
        return Bitmap.createScaledBitmap(source, width, height, true)
    }

    private fun isTitleValid(title: String): Boolean{
        if (title.isEmpty()) {
            _title.update {
                it.copy(errorState = R.string.error_required_field)
            }
            return false
        }
        else if(title.length < 4){
            _title.update {
                it.copy(errorState = R.string.error_title_short)
            }
            return false
        }
        else {
            _title.update {
                it.copy(errorState = 0)
            }
            return true
        }
    }

    private fun isDescriptionValid(description: String): Boolean{
        return if (description.isEmpty()) {
            _description.update {
                it.copy(errorState = R.string.error_required_field)
            }
            false
        }
        else if(description.length < 15){
            _description.update {
                it.copy(errorState = R.string.error_description_short)
            }
            false
        }
        else {
            _description.update {
                it.copy(errorState = 0)
            }
            true
        }
    }

    private fun isCategoryValid(category: String): Boolean{
        return if (category.isEmpty()) {
            _category.update {
                it.copy(errorState = R.string.error_required_field)
            }
            false
        }
        else {
            _category.update {
                it.copy(errorState = 0)
            }
            true
        }
    }

    private fun isImageValid(image: Uri?): Boolean {
        return if (image == null) {
            _image.update {
                it.copy(errorState = R.string.error_required_field)
            }
            false
        }
        else {
            _image.update {
                it.copy(errorState = 0)
            }
            true
        }
    }

    private fun isContentValid(content: String): Boolean{
        return if (content.isEmpty()) {
            _content.update {
                it.copy(errorState = R.string.error_required_field)
            }
            false
        }
        else if(content.length < 15){
            _content.update {
                it.copy(errorState = R.string.error_content_short)
            }
            false
        }
        else {
            _content.update {
                it.copy(errorState = 0)
            }
            true
        }
    }

    private fun enablePostButton(
        title: String,
        description: String,
        category: String,
        image: Uri?,
        content: String
    ): Boolean {
        return isTitleValid(title) &&
            isDescriptionValid(description) &&
            isCategoryValid(category) &&
            isImageValid(image) &&
            isContentValid(content)
    }
}