package com.twopiradrian.botanist.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R

@Composable
fun PostAuthor(
    author: String
) {
    Row(
        modifier = Modifier.height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight()
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodySmall
        )
    }
}