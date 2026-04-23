package com.hirebeat.com.app.danmon.feature.profile.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

@Composable
fun ProfileContactBar(profile: UserProfile?) {
    val uriHandler = LocalUriHandler.current

    val phone = profile?.links?.find { it.name.contains("Tele", true) || it.name.contains("phone", true) }?.ref
    val whatsapp = profile?.links?.find { it.name.contains("Wha", true) }?.ref
    val email = profile?.email

    println("Debug $phone , $whatsapp , $email" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ContactIconButton(Icons.Default.ChatBubbleOutline, Color(0xFFE2D1CA)) {
                whatsapp?.let {
                    val cleanNumber = it.filter { char -> char.isDigit() }
                    if (cleanNumber.isNotEmpty()) {
                        uriHandler.openUri("https://wa.me/$cleanNumber")
                    }
                }
            }


            ContactIconButton(Icons.Default.Phone, Color(0xFFFCEAE3)) {
                phone?.let {
                    val cleanPhone = it.filter { char -> char.isDigit() }
                    if (cleanPhone.isNotEmpty()) {
                        uriHandler.openUri("tel:$cleanPhone")
                    }
                }
            }

            ContactIconButton(Icons.Default.Email, Color(0xFFF0DFD8)) {
                email?.let {
                    uriHandler.openUri("mailto:$it")
                }
            }
        }
    }
}