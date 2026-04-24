package com.hirebeat.com.app.danmon.feature.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCalendarMaterial(
    onDateSelected: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        onDateSelected(datePickerState.selectedDateMillis)
    }

    DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = Color.Transparent,
            selectedDayContainerColor = Color(0xFF8D4E2C),
            selectedDayContentColor = Color.White,
            todayDateBorderColor = Color(0xFF8D4E2C),
            todayContentColor = Color(0xFF8D4E2C)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}