package com.twopiradrian.botanist.ui.components.molecules

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.app.ImageData
import com.twopiradrian.botanist.ui.components.atoms.SecondaryButton
import com.twopiradrian.botanist.ui.components.atoms.AnimatedError

@Composable
fun ImagePickerButton(
    state: ImageData,
    updateState: (uri: Uri?) -> Unit
) {

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            updateState(it)
        }
    )

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        AsyncImage(
            model = state.state ?: R.drawable.default_image,
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
        AnimatedError(visible = state.isError, errorState = R.string.error_required_field, paddingStart = 0.dp)
    }
}