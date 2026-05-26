package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.ui.theme.AccentPrimary
import com.juan.lazy.philipportfolio.ui.theme.CardBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.TextPrimary
import com.juan.lazy.philipportfolio.ui.theme.TextSecondary

@Composable
fun ExperienceCard(experience: Experience) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.Top) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(16.dp).background(AccentPrimary, CircleShape)
                )
                Box(
                    modifier = Modifier.width(2.dp).height(120.dp).background(AccentPrimary.copy(alpha = 0.2f))
                )
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
                            color = TextPrimary
                        )
                        Text(
                            text = experience.company,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AccentPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = experience.period,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                experience.highlights.forEach { highlight ->
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("▹", color = AccentPrimary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExperienceCardPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(16.dp)) {
            ExperienceCard(
                experience = Experience(
                    role = "Software Engineer",
                    company = "Tech Corp",
                    period = "2020 - 2023",
                    highlights = listOf("Developed key features", "Optimized performance")
                )
            )
        }
    }
}
