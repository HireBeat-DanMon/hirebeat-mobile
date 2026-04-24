package com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities

data class GigRequestItem(
    val id: String,
    val recruiterId: String,
    val musicianProfileId: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val paymentOffered: Double,
    val messageDetails: String?,
    val status: String
)