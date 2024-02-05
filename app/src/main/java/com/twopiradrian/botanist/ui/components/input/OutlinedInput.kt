@file:OptIn(ExperimentalComposeUiApi::class)

package com.twopiradrian.botanist.ui.components.input

import android.view.ViewTreeObserver
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun OutlinedInput(
    state: InputData,
    inputType: InputType,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit,
    isLastField: Boolean = false,
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
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(label), modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
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
            placeholder = { Text(text = stringResource(placeholder)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).border(
                width = 1.dp,
                color = if (state.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            ).shadow(1.dp, RoundedCornerShape(10.dp)),
        )
        AnimatedVisibility(visible = state.isError) {
            Text(
                text = if (state.isError) stringResource(state.errorState) else "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}