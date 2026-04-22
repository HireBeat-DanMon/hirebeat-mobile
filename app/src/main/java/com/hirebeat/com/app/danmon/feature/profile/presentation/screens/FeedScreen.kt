package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatTopBar
import com.hirebeat.com.app.danmon.core.theme.Sizing
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
        topBar = { HireBeatTopBar(title = "Descubrir Músicos") }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(Spacing.Medium),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                items(state.profiles) { profile ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable { onProfileClick(profile.id) }, //
//                        shape = RoundedCornerShape(Sizing.CardCorner)
//                    ) {
//                        Column(Modifier.padding(Spacing.Medium)) {
//                            Text(
//                                text = profile.fullName, //
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                            Text(
//                                text = profile.city, //
//                                style = MaterialTheme.typography.bodySmall
//                            )
//
//                            val mainInstruments = profile.instruments //
//                                .filter { it.isPrincipal }
//                                .joinToString { it.instrument.name }
//
//                            if (mainInstruments.isNotEmpty()) {
//                                Text(
//                                    text = mainInstruments,
//                                    color = MaterialTheme.colorScheme.primary,
//                                    style = MaterialTheme.typography.labelMedium
//                                )
//                            }
//                        }
//                    }

                    ProfileCard(
                        profile = profile,
                        onClick = { onProfileClick(profile.id) }
                    )

                }
            }
        }
    }
}