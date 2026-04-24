package com.hirebeat.com.app.danmon.feature.review.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hirebeat.com.app.danmon.core.theme.GoldStar

@Composable
fun RatingStars(rating: Float, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            val isFilled = index < rating.toInt()
            Icon(
                imageVector = if (isFilled) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = null,
                tint = GoldStar
            )
        }
    }
}