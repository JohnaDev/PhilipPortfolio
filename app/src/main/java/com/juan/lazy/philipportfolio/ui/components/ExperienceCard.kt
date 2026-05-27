package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceCard(experience: Experience) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(24.dp)

    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        Surface(
            onClick = { expanded = !expanded },
            shape = shape,
            color = PortfolioTheme.colors.cardBackground,
            border = BorderStroke(1.dp, PortfolioTheme.colors.textPrimary.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .padding(24.dp),
                verticalAlignment = Alignment.Top
            ) {
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
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun ExperienceCardLightPreview() {
    PhilipPortfolioTheme(appTheme = AppTheme.LIGHT) {
        Box(
            modifier = Modifier
                .background(PortfolioTheme.colors.background)
                .padding(16.dp)
        ) {
            ExperienceCard(
                experience = Experience(
                    role = "Senior Android Developer",
                    company = "Tech Solutions Inc.",
                    period = "January 2020 - Present • 4 years and 2 months",
                    highlights = listOf(
                        "Developed and maintained high-quality Android applications using Kotlin and Jetpack Compose.",
                        "Implemented modern architectural patterns (MVVM, Clean Architecture) to ensure scalability.",
                        "Mentored junior developers and conducted thorough code reviews."
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun ExperienceCardDarkPreview() {
    PhilipPortfolioTheme(appTheme = AppTheme.DARK) {
        Box(
            modifier = Modifier
                .background(PortfolioTheme.colors.background)
                .padding(16.dp)
        ) {
            ExperienceCard(
                experience = Experience(
                    role = "Android Developer",
                    company = "Innovation Labs",
                    period = "June 2018 - December 2020 • 2 years and 7 months",
                    highlights = listOf(
                        "Collaborated with cross-functional teams to define, design, and ship new features.",
                        "Optimized app performance and reduced crash rates by 30% through proactive debugging.",
                        "Integrated third-party APIs and libraries for enhanced functionality."
                    )
                )
            )
        }
    }
}
