package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@Composable
fun AboutMeSection(aboutMe: String) {
    Surface(
        color = PortfolioTheme.colors.cardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            aboutMe,
            style = MaterialTheme.typography.bodyMedium,
            color = PortfolioTheme.colors.textPrimary.copy(alpha = 0.9f),
            modifier = Modifier.padding(20.dp),
            lineHeight = 24.sp
        )
    }
}
