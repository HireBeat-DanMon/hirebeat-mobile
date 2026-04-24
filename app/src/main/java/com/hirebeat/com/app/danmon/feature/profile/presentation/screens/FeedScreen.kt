package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatTopBar
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.ProfileCard
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.FeedViewModel

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onProfileClick: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HireBeatTopBar(
                title = "Descubrir Músicos",
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                state.profiles.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No hay músicos disponibles",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = Spacing.Medium,
                            bottom = Spacing.ExtraLarge,
                            start = Spacing.Medium,
                            end = Spacing.Medium
                        ),
                        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
                    ) {
                        items(
                            items = state.profiles,
                            key = { it.id }
                        ) { profile ->
                            ProfileCard(
                                profile = profile,
                                onClick = { onProfileClick(profile.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}