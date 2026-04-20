package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import com.hirebeat.com.app.danmon.core.theme.*

@Composable
fun HireBeatButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Sizing.ButtonHeight),
        enabled = enabled,
        shape = RoundedCornerShape(Sizing.ButtonCorner),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Text(text = text.uppercase(), fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun PreviewButton() {
    HireBeatButton(text = "Entrar", onClick = {})
}