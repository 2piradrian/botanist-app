package com.twopiradrian.botanist.ui.components.input

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun FilledInput(
    modifier: Modifier = Modifier,
    state: InputData,
    inputType: InputType,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit,
    isLastField: Boolean = false,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val keyboardOptions = KeyboardOptions(
        keyboardType = when (inputType) {
            InputType.EMAIL    -> KeyboardType.Email
            InputType.PASSWORD -> KeyboardType.Password
            InputType.TEXT     -> KeyboardType.Text
        },
        imeAction = ImeAction.Done
    )

    val visualTransformation = when (inputType) {
        InputType.PASSWORD -> PasswordVisualTransformation()
        else               -> VisualTransformation.None
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.height(64.dp).fillMaxWidth().padding(bottom = 8.dp)
    ) {
        TextField(
            value = state.state,
            onValueChange = onValueChange,
            //
            maxLines = maxLines,
            singleLine = singleLine,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            keyboardActions = KeyboardActions(onDone = {
                if (isLastField) keyboardController?.hide()
                else focusManager.moveFocus(FocusDirection.Down)
            }),
            //
            placeholder = {
                Text(
                    text = stringResource(placeholder),
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = if (state.isError) MaterialTheme.colorScheme.error else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
        )
        AnimatedVisibility(visible = state.isError) {
            Text(
                text = if (state.isError) stringResource(state.errorState) else "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}