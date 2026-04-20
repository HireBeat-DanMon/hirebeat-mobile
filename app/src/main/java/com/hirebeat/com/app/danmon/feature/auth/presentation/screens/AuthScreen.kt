package com.hirebeat.com.app.danmon.feature.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.*
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.auth.presentation.components.RoleCard
import com.hirebeat.com.app.danmon.feature.auth.presentation.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: (Any) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            val destination = viewModel.getDestinationAfterAuth()
            onLoginSuccess(destination)
        }
    }

    Scaffold(
        topBar = {
            if (!state.isLoginMode) {
                HireBeatTopBar(
                    title = "Crear Cuenta",
                    onBackClick = { viewModel.toggleMode() }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
        ) {
            if (state.isLoginMode) {
                AuthHeaderSection()
            }

            Column(
                modifier = Modifier.padding(Spacing.Large),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                LoginRegisterSwitcher(
                    isLoginMode = state.isLoginMode,
                    onToggle = { viewModel.toggleMode() }
                )

                if (!state.isLoginMode) {
                    Text(
                        text = "¿Qué estás buscando?",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
                        RoleCard(
                            text = "Soy Músico",
                            isSelected = state.userRole == UserRole.MUSICIAN,
                            onClick = { viewModel.setRole(UserRole.MUSICIAN) },
                            modifier = Modifier.weight(1f)
                        )
                        RoleCard(
                            text = "Busco Músicos",
                            isSelected = state.userRole == UserRole.CLIENT,
                            onClick = { viewModel.setRole(UserRole.CLIENT) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HireBeatTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChanged,
                        label = "Nombre Completo",
                        imeAction = ImeAction.Next,
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                HireBeatTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChanged,
                    label = "Correo electrónico",
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                HireBeatTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChanged,
                    label = "Contraseña",
                    isPassword = true,
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(Spacing.Small))

                state.error?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                HireBeatButton(
                    text = if (state.isLoading) "Cargando..." else if (state.isLoginMode) "Entrar" else "Registrarse",
                    onClick = { viewModel.submit() }, // <-- CAMBIO CLAVE AQUI
                    enabled = !state.isLoading // Deshabilita el botón si está cargando
                )

            }
        }
    }


}

@Composable
fun AuthHeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "HireBeat",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Siente el ritmo. Encuentra a tu próximo músico de confianza.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = Spacing.ExtraLarge),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}