package com.hirebeat.com.app.danmon.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight

private val defaultTypography = Typography()

val AppTypography = Typography(

    displayMedium = defaultTypography.displayMedium.copy(
        fontWeight = FontWeight.Bold
    ),

    titleMedium = defaultTypography.titleMedium.copy(
        fontWeight = FontWeight.Bold
    ),

    labelLarge = defaultTypography.labelLarge.copy(
        fontWeight = FontWeight.Bold
    )
)