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
    // El estado se configura solo con la fecha actual del sistema
    val datePickerState = rememberDatePickerState()

    // Notifica al padre cada vez que el usuario toca un día diferente
    LaunchedEffect(datePickerState.selectedDateMillis) {
        onDateSelected(datePickerState.selectedDateMillis)
    }

    DatePicker(
        state = datePickerState,
        title = null, // Elimina el texto superior
        headline = null, // Elimina la fecha grande seleccionada
        showModeToggle = false, // Quita el icono de teclado
        colors = DatePickerDefaults.colors(
            containerColor = Color.Transparent, // 100% Transparente
            selectedDayContainerColor = Color(0xFF8D4E2C), // Color café de tu tema
            selectedDayContentColor = Color.White,
            todayDateBorderColor = Color(0xFF8D4E2C),
            todayContentColor = Color(0xFF8D4E2C)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}