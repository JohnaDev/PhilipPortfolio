package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@Composable
fun EducationSection(education: String) {
    Surface(
        color = PortfolioTheme.colors.cardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, PortfolioTheme.colors.accentTertiary.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = education,
            style = MaterialTheme.typography.bodyMedium,
            color = PortfolioTheme.colors.accentTertiary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
    }
}
