package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            .background(Color(0xFFF4EDE8))
            .padding(horizontal = Spacing.Medium)
    ) {
        Spacer(modifier = Modifier.height(Spacing.Large))

        Text(
            text = "Bandeja de\nSolicitudes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF8D4E2C),
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

            if (state.isLoading && state.requests.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF3B6B61)
                )
            }

            state.error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (!state.isLoading && state.requests.isEmpty()) {
                Text(
                    text = if (state.isReceivedTab)
                        "No tienes solicitudes recibidas aún."
                    else
                        "No has enviado ninguna solicitud.",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
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
    }
}