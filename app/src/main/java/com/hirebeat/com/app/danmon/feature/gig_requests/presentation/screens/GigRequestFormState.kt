package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import java.time.LocalDate
import java.time.LocalTime

data class GigRequestFormState(
    val selectedDate: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val location: String = "",
    val paymentOffered: String = "",
    val additionalDetails: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)