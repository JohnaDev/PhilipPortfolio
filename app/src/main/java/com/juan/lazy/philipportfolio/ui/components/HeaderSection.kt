package com.juan.lazy.philipportfolio.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.R
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.ui.theme.AccentPrimary
import com.juan.lazy.philipportfolio.ui.theme.AccentSecondary
import com.juan.lazy.philipportfolio.ui.theme.DarkBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.TextPrimary
import androidx.core.net.toUri

@Composable
fun HeaderSection(uiState: PortfolioUiState) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.linearGradient(listOf(AccentPrimary, AccentSecondary)),
                    shape = CircleShape
                )
                .padding(4.dp)
                .background(DarkBackground, CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = "Profile Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = uiState.name,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = uiState.role.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = AccentPrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactIcon(Icons.Filled.Email, uiState.email) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:${uiState.email}".toUri()
                }
                context.startActivity(intent)
            }
            ContactIcon(Icons.Filled.Phone, uiState.phone) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${uiState.phone}".toUri()
                }
                context.startActivity(intent)
            }
            ContactIcon(Icons.Filled.LocationOn, uiState.location) {
                val gmmIntentUri = "geo:0,0?q=${Uri.encode(uiState.location)}".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.1f), 
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPreview() {
    PhilipPortfolioTheme {
        HeaderSection(
            uiState = PortfolioUiState(
                name = "John Doe",
                role = "Senior Android Developer",
                email = "john@example.com",
                phone = "123-456-7890",
                location = "New York, USA",
                aboutMe = "",
                skills = emptyMap(),
                projects = emptyList(),
                experiences = emptyList(),
                education = "",
                languages = emptyList()
            )
        )
    }
}
