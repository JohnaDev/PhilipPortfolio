package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme


@Composable
fun ThemeSelectionSection(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Surface(
        color = PortfolioTheme.colors.cardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, PortfolioTheme.colors.textSecondary.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTheme.entries.forEach { theme ->
                val isSelected = currentTheme == theme
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected)
                                PortfolioTheme.colors.accentPrimary
                            else
                                PortfolioTheme.colors.textSecondary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onThemeSelected(theme) }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onThemeSelected(theme) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PortfolioTheme.colors.accentPrimary,
                            unselectedColor = PortfolioTheme.colors.textSecondary.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = theme.name.lowercase().replaceFirstChar { it.uppercase() },
                        color = if (isSelected) PortfolioTheme.colors.accentPrimary else PortfolioTheme.colors.textPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}