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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.SkillColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection(skills: Map<String, List<String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        skills.entries.forEachIndexed { index, (category, skillsList) ->
            Column {
                Text(
                    text = category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = PortfolioTheme.colors.textSecondary,
                    modifier = Modifier.padding(bottom = 8.dp),
                    letterSpacing = 1.sp
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skillsList.forEachIndexed { skillIndex, skill ->
                        val colorIndex = (index + skillIndex) % SkillColors.size
                        Surface(
                            color = PortfolioTheme.colors.cardBackground,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, SkillColors[colorIndex].copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .background(SkillColors[colorIndex], CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = skill,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PortfolioTheme.colors.textPrimary,
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
