package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {


    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val profile = state.userProfile

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        LaunchedEffect(userId) {
            viewModel.loadProfile(userId)
        }
        return
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                }
            }
        },
        bottomBar = {
            ProfileContactBar(profile)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {

                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-Spacing.Large)),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.Large),
                        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
                    ) {
                        ProfileHeaderSection(
                            name = profile?.fullName ?: "Usuario",
                            city = profile?.city ?: "Ubicación no disponible",
                            instruments = profile?.instruments ?: emptyList()
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(
                                alpha = 0.5f
                            )
                        )

                        SectionTitleView(title = "Sobre mí", iconColor = Color(0xFF8D4E2C))
                        Text(
                            text = profile?.description ?: "Sin descripción disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            color = Color.Black.copy(alpha = 0.8f)
                        )

                        if (profile?.genres?.isNotEmpty() == true) {
                            SectionTitleView(title = "Especialidad", iconColor = Color(0xFFC97E58))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                                verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                profile.genres.forEach { genre ->
                                    GenreChip(name = genre.name, isSelected = true, onClick = {})
                                }
                            }
                        }

                        SectionTitleView(
                            title = "Reseñas y Calificaciones",
                            iconColor = Color(0xFF5C4033)
                        )

                        ReviewCardMock()

                        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + Spacing.Small))
                    }
                }
            }
        }
    }
}
