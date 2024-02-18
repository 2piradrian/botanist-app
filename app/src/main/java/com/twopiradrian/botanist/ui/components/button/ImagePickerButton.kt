package com.twopiradrian.botanist.ui.components.button

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.text.AnimatedError

@Composable
fun ImagePickerButton(
    state: Uri?,
    onClick: () -> Unit
) {

    var uri by remember { mutableStateOf<Uri?>(null)}

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri = it }
    )

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        AsyncImage(
            model = if (uri != null) uri else R.drawable.default_image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        SecondaryButton(
            onClick = {
                singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            text = R.string.photo_label
        )
    }
}