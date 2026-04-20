package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*

// --- DATOS MOCK PARA VISUALIZACIÓN ---
private val mockGenres = listOf("Norteño", "Cumbia")
private val mockAboutMe = "Más de 10 años de experiencia tocando el acordeón de botones. He acompañado a varios grupos norteños y grabé dos discos. Busco grupo estable para fines de semana."
// -------------------------------------

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        // Usamos una TopBar transparente sobre la imagen
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                }
                IconButton(onClick = { /* Lógica de compartir */ }, modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)) {
                    Icon(Icons.Default.Share, contentDescription = "Compartir", tint = Color.White)
                }
            }
        },
        bottomBar = {
            // Barra de contacto flotante inferior
            ProfileContactBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
        ) {
            // Imagen de portada
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Cambiar por imagen real
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            // Contenedor de información (empieza sobre la imagen con bordes redondeados)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-Spacing.Large)) // Efecto de superposición
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Large),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
                ) {
                    // Header con Nombre, Estrellas y Ubicación
                    ProfileHeaderSection()

                    // Divider suave
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    // SECCIÓN: Sobre mí
                    SectionTitleView(title = "Sobre mí", iconColor = Color(0xFF8D4E2C))
                    Text(
                        text = mockAboutMe,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    // SECCIÓN: Especialidad (Géneros)
                    SectionTitleView(title = "Especialidad", iconColor = Color(0xFFC97E58))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        mockGenres.forEach { genre ->
                            // Usamos el GenreChip pero sin modo selección (isSelected = true para color estático)
                            GenreChip(name = genre, isSelected = true, onClick = {})
                        }
                    }

                    // SECCIÓN: Reseñas y Calificaciones (Visualización básica)
                    SectionTitleView(title = "Reseñas y Calificaciones", iconColor = Color(0xFF5C4033))
                    ReviewCardMock()

                    // Espacio para la barra de contacto inferior
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileHeaderSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Juan Carlos",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Garza",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Rating Card
            Surface(
                color = Color(0xFFFFDBCB).copy(alpha = 0.5f), // Color pastel del diseño
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB800), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("4.8", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                }
            }
        }
        Text("2 RESEÑAS", style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.align(Alignment.End))

        Spacer(modifier = Modifier.height(Spacing.Medium))

        // Instrumento y Nivel
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Acordeón", color = Color(0xFF8D4E2C), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = " • ", color = Color.Gray)
            Text(text = "Profesional", color = Color.Gray, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(Spacing.Medium))

        // Ubicación Chip
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFC97E58), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Monterrey, N.L.", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }
        }
    }
}

// Subcomponente para el título de sección con el indicador vertical coloreado
@Composable
fun SectionTitleView(title: String, iconColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.Small)
    ) {
        // Indicador vertical de color
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(iconColor)
        )
        Spacer(modifier = Modifier.width(Spacing.Small))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// Card de reseña Mock
@Composable
fun ReviewCardMock() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Grupo Los Regionales", fontWeight = FontWeight.Bold, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800), modifier = Modifier.size(14.dp))
                    Text("5", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text(
                "\"Excelente acordeonista, se aprendió el repertorio en dos días y tiene mucha presencia en el escenario.\"",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text("12 OCT 2023", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

// Barra de contacto inferior flotante
@Composable
fun ProfileContactBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(), // Respetar la barra de gestos del sistema
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactIconButton(Icons.Default.ChatBubbleOutline, Color(0xFFE2D1CA))
            ContactIconButton(Icons.Default.Phone, Color(0xFFFCEAE3))
            ContactIconButton(Icons.Default.Email, Color(0xFFF0DFD8))
        }
    }
}

@Composable
fun ContactIconButton(icon: ImageVector, backgroundColor: Color) {
    Surface(
        onClick = { /* Lógica de contacto */ },
        modifier = Modifier.size(60.dp),
        shape = CircleShape,
        color = backgroundColor,
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF8D4E2C), // Color primario marrón
                modifier = Modifier.size(28.dp)
            )
        }
    }
}