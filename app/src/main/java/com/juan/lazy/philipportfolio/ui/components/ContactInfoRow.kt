package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme


@Composable
fun ContactInfoRow(uiState: PortfolioUiState) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                uiState.email,
                color = PortfolioTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                uiState.phone,
                color = PortfolioTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                uiState.location,
                color = PortfolioTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}