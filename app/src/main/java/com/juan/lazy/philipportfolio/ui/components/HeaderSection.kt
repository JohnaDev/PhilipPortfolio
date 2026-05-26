package com.juan.lazy.philipportfolio.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.juan.lazy.philipportfolio.R
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@Composable
fun HeaderSection(uiState: PortfolioUiState) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    brush = Brush.linearGradient(listOf(PortfolioTheme.colors.accentPrimary, PortfolioTheme.colors.accentSecondary)),
                    shape = CircleShape
                )
                .padding(4.dp)
                .background(PortfolioTheme.colors.background, CircleShape)
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = uiState.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = PortfolioTheme.colors.textPrimary
        )
        Text(
            text = uiState.role.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = PortfolioTheme.colors.accentPrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
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
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(
            color = PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f), 
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }
}
