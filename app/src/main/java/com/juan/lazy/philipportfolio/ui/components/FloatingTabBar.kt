package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.getIconForTab
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme


@Composable
fun FloatingTabBar(
    tabs: List<String>,
    selectedIndex: Int,
    isExpanded: Boolean,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        color = PortfolioTheme.colors.cardBackground.copy(alpha = 0.98f),
        tonalElevation = 12.dp,
        border = BorderStroke(1.dp, PortfolioTheme.colors.textSecondary.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isExpanded) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedIndex == index

                    val backgroundColor by animateColorAsState(
                        targetValue = if (isSelected) PortfolioTheme.colors.accentPrimary.copy(alpha = 0.15f)
                        else Color.Transparent,
                        label = "bg"
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) PortfolioTheme.colors.accentPrimary
                        else PortfolioTheme.colors.textSecondary,
                        label = "content"
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(backgroundColor)
                            .clickable { onTabSelected(index) }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                getIconForTab(index),
                                contentDescription = title,
                                tint = contentColor,
                                modifier = Modifier.size(24.dp)
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = contentColor,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false
                                )
                            }
                        }
                    }
                }
            } else {
                // Minimized state: Only show the active tab icon
                val title = tabs[selectedIndex]
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(PortfolioTheme.colors.accentPrimary.copy(alpha = 0.15f))
                        .clickable { onTabSelected(selectedIndex) }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        getIconForTab(selectedIndex),
                        contentDescription = title,
                        tint = PortfolioTheme.colors.accentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}