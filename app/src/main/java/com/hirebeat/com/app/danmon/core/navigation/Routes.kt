package com.hirebeat.com.app.danmon.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

@Serializable
object SaveProfileRoute

@Serializable
object HomeRoute

@Serializable
object MyProfileRoute

@Serializable
object FeedRoute

@Serializable
object MyReviewsRoute

@Serializable
data class ProfileDetailRoute(val userId: String)