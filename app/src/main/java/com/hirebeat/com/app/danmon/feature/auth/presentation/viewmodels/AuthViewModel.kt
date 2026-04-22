package com.hirebeat.com.app.danmon.feature.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.core.navigation.*
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.LoginRequestDto
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.RegisterRequestDto
import com.hirebeat.com.app.danmon.feature.auth.domain.usecases.LoginUseCase
import com.hirebeat.com.app.danmon.feature.auth.domain.usecases.RegisterUseCase
import com.hirebeat.com.app.danmon.feature.auth.presentation.screens.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(newValue: String) = _uiState.update { it.copy(email = newValue, error = null) }
    fun onPasswordChanged(newValue: String) = _uiState.update { it.copy(password = newValue, error = null) }
    fun onNameChanged(newValue: String) = _uiState.update { it.copy(name = newValue, error = null) }
    fun setRole(role: UserRole) = _uiState.update { it.copy(userRole = role) }

    fun toggleMode() = _uiState.update { it.copy(isLoginMode = !it.isLoginMode, error = null) }

    fun submit() {
        val currentState = _uiState.value

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(error = "Llena todos los campos obligatorios") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                if (currentState.isLoginMode) {
                    val request = LoginRequestDto(currentState.email, currentState.password)
                    loginUseCase.execute(request)
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }

                } else {
                    val roleId = when(currentState.userRole) {
                        UserRole.MUSICIAN -> "22222222-2222-2222-2222-222222222222"
                        UserRole.CLIENT -> "11111111-1111-1111-1111-111111111111"
                        else -> "0"
                    }

                    val request = RegisterRequestDto(
                        fullname = currentState.name,
                        email = currentState.email,
                        password = currentState.password,
                        roleId = roleId
                    )
                    registerUseCase.execute(request)

                    _uiState.update { it.copy(
                        isLoading = false,
                        isLoginMode = true,
                        password = "",
                        error = "Registro exitoso. Ya puedes iniciar sesión."
                    ) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Error de conexión") }
            }
        }
    }

    fun getDestinationAfterAuth(): Any {
        return HomeRoute
    }
}