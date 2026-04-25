package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.GigRequestCard
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.RequestsToggle
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.viewmodel.GigRequestsListViewModel

@Composable
fun GigRequestsScreen(
    viewModel: GigRequestsListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Spacing.Medium)
    ) {
        Spacer(modifier = Modifier.height(Spacing.Large))

        Text(
            text = "Bandeja de\nSolicitudes",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = Spacing.Large)
        )

        RequestsToggle(
            isReceivedSelected = state.isReceivedTab,
            onToggle = { isReceived ->
                viewModel.fetchRequests(isReceived)
            }
        )

        Spacer(modifier = Modifier.height(Spacing.Large))

        Box(modifier = Modifier.fillMaxSize()) {

            when {
                state.isLoading && state.requests.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                state.error != null && state.requests.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = Spacing.Large),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(Spacing.Medium))
                        Button(
                            onClick = { viewModel.fetchRequests(state.isReceivedTab) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                text = "Reintentar",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

                !state.isLoading && state.error == null && state.requests.isEmpty() -> {
                    Text(
                        text = if (state.isReceivedTab)
                            "No tienes solicitudes recibidas aún."
                        else
                            "No has enviado ninguna solicitud.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }

                state.requests.isNotEmpty() -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
                    ) {
                        items(
                            items = state.requests,
                            key = { it.id }
                        ) { request ->
                            GigRequestCard(
                                request = request,
                                isMusician = state.isReceivedTab,
                                onAccept = {
                                    viewModel.updateStatus(request.id, "ACCEPTED")
                                },
                                onReject = {
                                    viewModel.updateStatus(request.id, "REJECTED")
                                }
                            )
                        }
                    }
                }
            }

            if (state.isLoading && state.requests.isNotEmpty()) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}