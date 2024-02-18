package com.twopiradrian.botanist.ui.screens.write

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.input.InputData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class WriteViewModel: ViewModel() {
    private val _title = MutableStateFlow(InputData.empty())
    val title: MutableStateFlow<InputData> = _title

    private val _description = MutableStateFlow(InputData.empty())
    val description: MutableStateFlow<InputData> = _description

    private val _category = MutableStateFlow(InputData.empty())
    val category: MutableStateFlow<InputData> = _category

    private val _image = MutableStateFlow<Uri?>(null)
    val image: MutableStateFlow<Uri?> = _image

    private val _content = MutableStateFlow(InputData.empty())
    val content: MutableStateFlow<InputData> = _content

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: MutableStateFlow<Boolean> = _isButtonEnabled

    private val _error = MutableStateFlow(0)
    val error: MutableStateFlow<Int> = _error

    fun changeErrorState() {
        _error.value = 0
    }

    fun onPostChange(
        title: String,
        description: String,
        category: String,
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
        _isButtonEnabled.value = title.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty() && content.isNotEmpty()
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
        else if(title.length > 21){
            _title.update {
                it.copy(errorState = R.string.error_title_long)
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
        else if(description.length > 101){
            _description.update {
                it.copy(errorState = R.string.error_description_long)
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
        else if(content.length > 1001){
            _content.update {
                it.copy(errorState = R.string.error_content_long)
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
}