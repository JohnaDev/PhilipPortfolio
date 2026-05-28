package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.SyncStatus
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme


@Composable
fun SyncingIndicator(syncStatus: SyncStatus) {
    AnimatedVisibility(
        visible = syncStatus != SyncStatus.IDLE,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val (backgroundColor, contentColor, text, icon) = when (syncStatus) {
                SyncStatus.SYNCING -> listOf(
                    PortfolioTheme.colors.accentPrimary.copy(alpha = 0.1f),
                    PortfolioTheme.colors.accentPrimary,
                    "Updating...",
                    null
                )

                SyncStatus.SUCCESS -> listOf(
                    Color(0xFF22C55E).copy(alpha = 0.1f),
                    Color(0xFF22C55E),
                    "Up to date",
                    Icons.Default.CheckCircle
                )

                SyncStatus.ERROR -> listOf(
                    MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                    MaterialTheme.colorScheme.error,
                    "Sync failed",
                    Icons.Default.Error
                )

                else -> listOf(Color.Transparent, Color.Transparent, "", null)
            }

            Surface(
                color = backgroundColor as Color,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, (contentColor as Color).copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (icon == null) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(12.dp),
                            strokeWidth = 2.dp,
                            color = contentColor
                        )
                    } else {
                        Icon(
                            imageVector = icon as ImageVector,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = contentColor
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = text as String,
                        style = MaterialTheme.typography.labelSmall,
                        color = contentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}