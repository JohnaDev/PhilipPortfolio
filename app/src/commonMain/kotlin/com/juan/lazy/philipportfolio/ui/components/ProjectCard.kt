package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.model.Project
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme
import com.juan.lazy.philipportfolio.util.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectCard(project: Project, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(24.dp)

    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        Surface(
            onClick = { expanded = !expanded },
            shape = shape,
            color = PortfolioTheme.colors.cardBackground,
            border = BorderStroke(1.dp, PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f)),
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .padding(24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(PortfolioTheme.colors.accentPrimary.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            project.title.take(1), 
                            color = PortfolioTheme.colors.accentPrimary, 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PortfolioTheme.colors.textPrimary
                        )
                        Text(
                            text = project.role,
                            style = MaterialTheme.typography.bodyMedium,
                            color = PortfolioTheme.colors.textSecondary
                        )
                    }
                    
                    project.link?.let { link ->
                        IconButton(
                            onClick = {
                                openUrl(link)
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.OpenInNew,
                                contentDescription = "Open Link",
                                tint = PortfolioTheme.colors.accentPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Icon(
                        if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = PortfolioTheme.colors.textSecondary
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = PortfolioTheme.colors.accentTertiary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, PortfolioTheme.colors.accentTertiary.copy(alpha = 0.2f))
                ) {
                    Text(
                        text = project.technologies,
                        style = MaterialTheme.typography.labelMedium,
                        color = PortfolioTheme.colors.accentTertiary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                
                if (expanded) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = project.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PortfolioTheme.colors.textPrimary.copy(alpha = 0.8f),
                        lineHeight = 24.sp
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Key Contributions",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = PortfolioTheme.colors.accentSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    project.keyContributions.forEach { contribution ->
                        Row(modifier = Modifier.padding(bottom = 8.dp)) {
                            Text("•", color = PortfolioTheme.colors.accentSecondary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = contribution,
                                style = MaterialTheme.typography.bodySmall,
                                color = PortfolioTheme.colors.textSecondary,
                                lineHeight = 20.sp
                            )
                        }
                    }
                    
                    project.note?.let {
                        Spacer(modifier = Modifier.height(20.dp))
                        Surface(
                            color = PortfolioTheme.colors.textPrimary.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f))
                        ) {
                            Text(
                                text = "ℹ️ $it",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(16.dp),
                                fontStyle = FontStyle.Italic,
                                color = PortfolioTheme.colors.textSecondary
                            )
                        }
                    }
                    
                    project.link?.let { link ->
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                openUrl(link)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PortfolioTheme.colors.accentPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View App", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
