@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.twopiradrian.botanist.ui.components.atoms

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun OutlinedInput(
    state: InputData,
    inputType: InputType,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit,
    isLastField: Boolean = false,
    icon: ImageVector?,
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(label),
            modifier = Modifier.padding(start = 16.dp, bottom = 2.dp),
            style = MaterialTheme.typography.labelLarge
        )
        TextField(
            value = state.state,
            onValueChange = onValueChange,
            //
            maxLines = 1,
            singleLine = true,
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
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                if (icon != null) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (state.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .border(
                    width = 2.dp,
                    color = if (state.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(22.dp)
                )
                .shadow(1.dp, RoundedCornerShape(22.dp)),
        )
        AnimatedError(
            visible = state.isError,
            errorState = state.errorState,
            paddingStart = 16.dp
        )
    }
}