package com.twopiradrian.botanist.ui.components.organisms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.components.atoms.TitleLarge

@Composable
fun PostHeader(
    title: String,
    description: String,
    category: String
){
    TitleLarge(
        text = if (title == "") {
            stringResource(id = R.string.placeholder_title)
        }
        else {
            title
        },
    )
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Text(
        text = if (description == "") {
            stringResource(id = R.string.placeholder_description)
        } else {
            description
        },
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Row{
        Text(
            text = stringResource(id = R.string.post_category),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (category == "") {
                stringResource(id = R.string.placeholder_category)
            } else {
                stringResource(id = Categories.valueOf(category).category)
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}