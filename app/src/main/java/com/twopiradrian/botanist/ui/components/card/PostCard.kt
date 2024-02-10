package com.twopiradrian.botanist.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.twopiradrian.botanist.R

@Composable
fun PostCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .height(400.dp)
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        onClick = {
            onClick()
        }
    ) {
        CardHeader()
        CardContent()
    }
}

@Composable
fun CardHeader() {
    Row(
        modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "carlitos jimenez",
            modifier = Modifier.fillMaxHeight(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
    Divider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
fun CardContent(){
    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "This is a post",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 22.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "This is a very very very very very very very very very very very long description...",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(12.dp))
        AsyncImage(
            model = "https://uning.es/wp-content/uploads/2016/08/ef3-placeholder-image.jpg",
            contentDescription = null,
            modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}