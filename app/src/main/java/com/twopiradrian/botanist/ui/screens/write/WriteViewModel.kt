package com.twopiradrian.botanist.ui.screens.write

import androidx.lifecycle.ViewModel
import com.twopiradrian.botanist.ui.components.input.InputData
import kotlinx.coroutines.flow.MutableStateFlow

class WriteViewModel: ViewModel() {
    private val _title = MutableStateFlow(InputData.empty())
    val title: MutableStateFlow<InputData> = _title

    private val _description = MutableStateFlow(InputData.empty())
    val description: MutableStateFlow<InputData> = _description

    private val _category = MutableStateFlow(InputData.empty())
    val category: MutableStateFlow<InputData> = _category

    private val _content = MutableStateFlow(InputData.empty())
    val content: MutableStateFlow<InputData> = _content

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: MutableStateFlow<Boolean> = _isButtonEnabled

    private val _error = MutableStateFlow(0)
    val error: MutableStateFlow<Int> = _error

    fun changeErrorState() {
        _error.value = 0
    }
}