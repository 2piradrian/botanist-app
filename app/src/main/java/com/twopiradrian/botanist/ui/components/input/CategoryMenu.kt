@file:OptIn(ExperimentalMaterial3Api::class)

package com.twopiradrian.botanist.ui.components.input

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories

@Composable
fun CategoryMenu(
    state: InputData,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(Categories.INDOOR) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        FilledInput(
            state = state,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            readOnly = true,
            inputType = InputType.TEXT,
            placeholder = R.string.category_label,
            onValueChange = {},
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            Categories.entries.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = it.category),
                        )
                    },
                    onClick = {
                        category = it
                        isExpanded = false
                    }
                )
            }
        }
    }
}