@file:OptIn(ExperimentalMaterial3Api::class)

package com.twopiradrian.botanist.ui.components.atoms

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.twopiradrian.botanist.ui.app.InputData
import com.twopiradrian.botanist.ui.app.InputType

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
        modifier = modifier.fillMaxWidth()
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
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    ),
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .height(if (maxLines == 1) 56.dp else 120.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
        AnimatedError(
            visible = state.isError,
            errorState = state.errorState,
            paddingStart = 5.dp
        )
    }
}