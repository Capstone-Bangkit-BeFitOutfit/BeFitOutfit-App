package com.bangkit.befitoutfit.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.befitoutfit.R
import com.bangkit.befitoutfit.data.model.Outfit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemOutfit(
    outfit: Outfit,
    modifier: Modifier = Modifier,
    onClick: (Outfit) -> Unit = {},
) {
    Card(
        onClick = { onClick(outfit) },
        modifier = modifier.fillMaxWidth(),
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(
                        weight = 1f,
                    )
                    .padding(
                        all = 16.dp,
                    ),
            ) {
                Text(
                    text = outfit.name,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(
                    modifier = Modifier.padding(
                        all = 4.dp,
                    )
                )

                Text(
                    text = "Type: ${outfit.type}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(
                    context = LocalContext.current,
                ).data(
                    data = outfit.imageUrl,
                ).crossfade(
                    enable = true,
                ).build(),
                contentDescription = "${outfit.name}'s outfit image",
                modifier = Modifier.size(
                    size = 100.dp,
                ),
                placeholder = painterResource(
                    id = R.drawable.image_placeholder,
                ),
                error = rememberVectorPainter(
                    image = Icons.Outlined.ErrorOutline,
                ),
            )
        }
    }
}
