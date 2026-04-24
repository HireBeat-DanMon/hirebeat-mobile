package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.*
import com.hirebeat.com.app.danmon.core.theme.*
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
        containerColor = MaterialTheme.colorScheme.background,
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
                imageUrl = state.userProfile?.photoUrl,
                onImageSelected = { bytes, name ->
                    viewModel.onImageSelected(bytes, name)
                }
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                HireBeatTextField(
                    value = state.city,
                    onValueChange = viewModel::onCityChange,
                    label = "Ubicación",
                    trailingIcon = {
                        IconButton(onClick = viewModel::useCurrentLocation) {
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
                    onValueChange = viewModel::onExperienceChange,
                    label = "Años de experiencia"
                )
                HireBeatTextField(
                    value = state.phone,
                    onValueChange = viewModel::onPhoneChange,
                    label = "Teléfono"
                )
                HireBeatTextField(
                    value = state.whatsapp,
                    onValueChange = viewModel::onWhatsAppChange,
                    label = "WhatsApp"
                )
                HireBeatTextField(
                    value = state.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = "Cuéntanos sobre ti"
                )
            }

            SectionTitleView(title = "Instrumentos y Experiencia")

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                Text(
                    text = "Tip: Doble clic para marcar como principal",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp)
                )

                state.selectedInstruments.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
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
                        IconButton(
                            onClick = { viewModel.removeInstrument(item.instrument.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.onShowModal(true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(Sizing.CardCorner),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Agregar del catálogo")
                }
            }

            SectionTitleView(title = "Géneros Musicales")
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

            SectionTitleView(title = "Portafolio")
            HireBeatTextField(
                value = state.instagramUser,
                onValueChange = viewModel::onInstagramChange,
                label = "Usuario de Instagram",
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            state.error?.let { errorMsg ->
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            HireBeatButton(
                text = if (state.isSaving) "Guardando..." else "Guardar perfil",
                onClick = viewModel::saveProfile,
                enabled = !state.isSaving,
                modifier = Modifier.padding(bottom = Spacing.ExtraLarge)
            )
        }

        if (state.showInstrumentModal) {
            AlertDialog(
                onDismissRequest = { viewModel.onShowModal(false) },
                title = { Text("Selecciona un instrumento") },
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                text = {
                    LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                        items(state.availableInstruments) { instrument ->
                            ListItem(
                                headlineContent = { Text(instrument.name) },
                                modifier = Modifier.clickable {
                                    viewModel.addInstrument(instrument)
                                }
                            )
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