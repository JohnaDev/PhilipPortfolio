package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@Composable
fun ExperienceCard(experience: Experience) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PortfolioTheme.colors.cardBackground),
        border = BorderStroke(1.dp, PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f))
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.Top) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(PortfolioTheme.colors.accentPrimary, CircleShape)
                )
                if (expanded) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(100.dp)
                            .background(PortfolioTheme.colors.accentPrimary.copy(alpha = 0.2f))
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = experience.role,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PortfolioTheme.colors.textPrimary
                        )
                        Text(
                            text = experience.company,
                            style = MaterialTheme.typography.bodyMedium,
                            color = PortfolioTheme.colors.accentPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = experience.period,
                            style = MaterialTheme.typography.labelSmall,
                            color = PortfolioTheme.colors.textSecondary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Icon(
                        if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = PortfolioTheme.colors.textSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    experience.highlights.forEach { highlight ->
                        Row(modifier = Modifier.padding(bottom = 8.dp)) {
                            Text("▹", color = PortfolioTheme.colors.accentPrimary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = highlight,
                                style = MaterialTheme.typography.bodyMedium,
                                color = PortfolioTheme.colors.textSecondary,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
