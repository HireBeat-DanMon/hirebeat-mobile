package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.*
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SaveProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onProfileSaved: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onProfileSaved()
    }

    Scaffold(
        topBar = {
            HireBeatTopBar(title = "Configuración de Perfil", onBackClick = onBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.Large)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.Large)
        ) {
            ProfileImageSection(
                imageUrl = state.userProfile?.photoUrl, // Usar la URL que viene del estado
                onImageSelected = { bytes, name ->
                    viewModel.onImageSelected(bytes, name)
                }
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                HireBeatTextField(
                    value = state.city,
                    onValueChange = { viewModel.onCityChange(it) },
                    label = "Ubicación",
                    trailingIcon = {
                        IconButton(onClick = { viewModel.useCurrentLocation() }) {
                            Icon(
                                imageVector = Icons.Default.MyLocation,
                                contentDescription = "Obtener ubicación",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                HireBeatTextField(
                    value = state.experience,
                    onValueChange = { viewModel.onExperienceChange(it) },
                    label = "Años de experiencia"
                )
                HireBeatTextField(
                    value = state.phone,
                    onValueChange = { viewModel.onPhoneChange(it) },
                    label = "Teléfono"
                )
                HireBeatTextField(
                    value = state.whatsapp,
                    onValueChange = { viewModel.onWhatsAppChange(it) },
                    label = "WhatsApp"
                )
                HireBeatTextField(
                    value = state.description,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = "Cuéntanos sobre ti"
                )
            }

            SectionTitle("INSTRUMENTOS Y EXPERIENCIA")

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                Text(
                    "Tip: Doble clic para marcar como principal",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                state.selectedInstruments.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            InstrumentItem(
                                profileInstrument = item,
                                onLevelChange = { newLevel ->
                                    viewModel.updateInstrumentLevel(item.instrument.id, newLevel)
                                },
                                onTogglePrincipal = {
                                    viewModel.togglePrincipalInstrument(item.instrument.id)
                                }
                            )
                        }
                        IconButton(onClick = { viewModel.removeInstrument(item.instrument.id) }) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.onShowModal(true) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(Sizing.CardCorner),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("AGREGAR DEL CATÁLOGO", color = MaterialTheme.colorScheme.primary)
                }
            }

            SectionTitle("GÉNEROS MUSICALES")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                state.availableGenres.forEach { genre ->
                    GenreChip(
                        name = genre.name,
                        isSelected = state.selectedGenres.contains(genre.id),
                        onClick = { viewModel.toggleGenre(genre.id) }
                    )
                }
            }

            SectionTitle("PORTAFOLIO")
            HireBeatTextField(
                value = state.instagramUser,
                onValueChange = { viewModel.onInstagramChange(it) },
                label = "Usuario de Instagram",
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            state.error?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }

            HireBeatButton(
                text = if (state.isSaving) "GUARDANDO..." else "GUARDAR PERFIL",
                onClick = { viewModel.saveProfile() },
                enabled = !state.isSaving
            )
        }

        if (state.showInstrumentModal) {
            AlertDialog(
                onDismissRequest = { viewModel.onShowModal(false) },
                title = { Text("Selecciona un instrumento") },
                text = {
                    LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                        items(state.availableInstruments) { instrument ->
                            TextButton(
                                onClick = { viewModel.addInstrument(instrument) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(instrument.name)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.onShowModal(false) }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}