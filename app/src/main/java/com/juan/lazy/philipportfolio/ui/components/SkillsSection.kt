package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.ui.theme.CardBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.SkillColors
import com.juan.lazy.philipportfolio.ui.theme.TextPrimary
import com.juan.lazy.philipportfolio.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection(skills: Map<String, List<String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        skills.entries.forEachIndexed { index, (category, skillsList) ->
            Column {
                Text(
                    text = category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp),
                    letterSpacing = 1.sp
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    skillsList.forEachIndexed { skillIndex, skill ->
                        val colorIndex = (index + skillIndex) % SkillColors.size
                        Surface(
                            color = CardBackground,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, SkillColors[colorIndex].copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(SkillColors[colorIndex], CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = skill,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkillsSectionPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(16.dp)) {
            SkillsSection(
                skills = mapOf(
                    "Languages" to listOf("Kotlin", "Java", "Swift"),
                    "Tools" to listOf("Android Studio", "Git", "Firebase")
                )
            )
        }
    }
}
