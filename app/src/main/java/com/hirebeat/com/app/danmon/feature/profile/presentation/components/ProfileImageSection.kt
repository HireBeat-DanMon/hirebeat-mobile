package com.hirebeat.com.app.danmon.feature.profile.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileImageSection(
    imageUrl: String?,
    onImageSelected: (ByteArray, String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bytes = context.contentResolver.openInputStream(it)?.readBytes()
            if (bytes != null) onImageSelected(bytes, "profile_${System.currentTimeMillis()}.jpg")
        }
    }

    val density = LocalDensity.current
    val dashWidth = with(density) { 10.dp.toPx() }
    val dashGap = with(density) { 10.dp.toPx() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { launcher.launch("image/*") } // Al tocar, abre la galería
    ) {
        Canvas(modifier = Modifier.size(120.dp)) {
            drawCircle(
                color = Color(0xFF8D4E2C),
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap))
                )
            )
        }
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (!imageUrl.isNullOrEmpty()) {
                    // 2. Si hay URL, mostramos la imagen real
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // 3. Si no, mostramos el icono por defecto
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.PhotoCamera, null, tint = Color.Gray)
                        Text("FOTO", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}