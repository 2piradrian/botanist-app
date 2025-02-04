@file:OptIn(ExperimentalMaterial3Api::class)

package com.twopiradrian.botanist.ui.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.app.InputData
import com.twopiradrian.botanist.ui.app.InputType

@Composable
fun CategoryMenu(
    state: InputData,
    onValueChange: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(Categories.INDOOR) }

    val selected = if (state.state.isEmpty()) {
        InputData.empty()
    } else{
       InputData(stringResource(id = category.category), state.isError, state.errorState)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = it
            },
        ) {
            FilledInput(
                state = selected,
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
                            onValueChange(category.name)
                        }
                    )
                }
            }
        }
        AnimatedError(
            visible = state.isError,
            errorState = state.errorState,
            paddingStart = 5.dp
        )
    }

}