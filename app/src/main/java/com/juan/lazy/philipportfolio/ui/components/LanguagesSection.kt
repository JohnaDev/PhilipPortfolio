package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguagesSection(languages: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        languages.forEach { language ->
            Surface(
                color = PortfolioTheme.colors.cardBackground,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, PortfolioTheme.colors.accentSecondary.copy(alpha = 0.2f))
            ) {
                Text(
                    text = language,
                    color = PortfolioTheme.colors.textPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
