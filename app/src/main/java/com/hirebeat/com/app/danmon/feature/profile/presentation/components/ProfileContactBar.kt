package com.hirebeat.com.app.danmon.feature.profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

@Composable
fun ProfileContactBar(profile: UserProfile?) {
    val uriHandler = LocalUriHandler.current

    val phone = profile?.links?.find { it.name.contains("Tele", true) || it.name.contains("phone", true) }?.ref
    val whatsapp = profile?.links?.find { it.name.contains("Wha", true) }?.ref
    val email = profile?.email

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ContactIconButton(
                icon = Icons.Default.ChatBubbleOutline,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    whatsapp?.let {
                        val cleanNumber = it.filter { char -> char.isDigit() }
                        if (cleanNumber.isNotEmpty()) {
                            uriHandler.openUri("https://wa.me/$cleanNumber")
                        }
                    }
                }
            )


            ContactIconButton(
                icon = Icons.Default.Phone,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    phone?.let {
                        val cleanPhone = it.filter { char -> char.isDigit() }
                        if (cleanPhone.isNotEmpty()) {
                            uriHandler.openUri("tel:$cleanPhone")
                        }
                    }
                }
            )

            ContactIconButton(
                icon = Icons.Default.Email,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = {
                    email?.let {
                        uriHandler.openUri("mailto:$it")
                    }
                }
            )
        }
    }
}