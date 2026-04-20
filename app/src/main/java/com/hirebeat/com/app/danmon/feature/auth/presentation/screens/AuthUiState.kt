package com.hirebeat.com.app.danmon.feature.auth.presentation.screens

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoginMode: Boolean = true,
    val userRole: UserRole = UserRole.NONE,

    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

enum class UserRole { MUSICIAN, CLIENT, NONE }