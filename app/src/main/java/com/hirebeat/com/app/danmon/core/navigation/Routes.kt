package com.hirebeat.com.app.danmon.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

@Serializable
object SaveProfileRoute

@Serializable
object HomeRoute // Para el Feed principal

@Serializable
object MyProfileRoute

@Serializable
data class ProfileDetailRoute(val userId: String)