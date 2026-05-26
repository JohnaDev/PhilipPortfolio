package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.ui.theme.AccentPrimary
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme

@Composable
fun ContactIcon(icon: ImageVector, text: String, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
    ) {
        Icon(icon, contentDescription = text, tint = AccentPrimary)
    }
}

@Preview
@Composable
fun ContactIconPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier
            .background(Color(0xFF0F172A))
            .padding(16.dp)) {
            ContactIcon(
                icon = Icons.Filled.Email,
                text = "john@example.com",
                onClick = {}
            )
        }
    }
}
