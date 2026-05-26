package com.juan.lazy.philipportfolio.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import com.juan.lazy.philipportfolio.ui.theme.*

@Composable
fun PortfolioScreen(
    uiState: PortfolioUiState,
    modifier: Modifier = Modifier
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(DarkBackground, Color.Black)
    )

    Box(modifier = modifier
        .fillMaxSize()
        .background(gradientBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HeaderSection(uiState) }
            
            item { SectionHeader("🧭 About Me", Icons.Filled.Person) }
            item { 
                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        uiState.aboutMe, 
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            item { SectionHeader("🛠️ Technical Skills", Icons.Filled.Build) }
            item { SkillsSection(uiState.skills) }
            
            item { SectionHeader("📱 Featured Projects", Icons.Filled.Star) }
            itemsIndexed(uiState.projects) { _, project ->
                ProjectCard(project)
            }
            
            item { SectionHeader("💼 Professional Experience", Icons.Filled.Work) }
            itemsIndexed(uiState.experiences) { _, experience ->
                ExperienceCard(experience)
            }

            item { SectionHeader("🎓 Education", Icons.AutoMirrored.Filled.MenuBook) }
            item { 
                Surface(
                    color = VibrantAmber.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = uiState.education,
                        style = MaterialTheme.typography.bodyLarge,
                        color = VibrantAmber,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item { SectionHeader("🌐 Languages", Icons.Filled.Language) }
            item { 
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.languages.forEach { language ->
                        AssistChip(
                            onClick = {},
                            label = { Text(language, color = Color.White) },
                            colors = AssistChipDefaults.assistChipColors(containerColor = DeepOcean.copy(alpha = 0.4f)),
                            border = AssistChipDefaults.assistChipBorder(borderColor = SoftCyan, enabled = true)
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(48.dp)) }
        }
    }
}

@Composable
private fun HeaderSection(uiState: PortfolioUiState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(VibrantAmber, CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = DarkBackground)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = uiState.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = VibrantAmber
        )
        Text(
            text = uiState.role,
            style = MaterialTheme.typography.titleLarge,
            color = SoftCyan,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ContactIcon(Icons.Filled.Email, uiState.email)
            ContactIcon(Icons.Filled.Phone, uiState.phone)
            ContactIcon(Icons.Filled.LocationOn, uiState.location)
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 1.dp)
    }
}

@Composable
private fun ContactIcon(icon: ImageVector, text: String) {
    IconButton(
        onClick = { /* Action */ },
        modifier = Modifier.background(Color.White.copy(alpha = 0.1f), CircleShape)
    ) {
        Icon(icon, contentDescription = text, tint = Color.White)
    }
}

@Composable
private fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = VibrantAmber, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillsSection(skills: Map<String, List<String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        skills.entries.forEachIndexed { index, (category, skillsList) ->
            Column {
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = SoftCyan,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skillsList.forEachIndexed { skillIndex, skill ->
                        val colorIndex = (index + skillIndex) % SkillColors.size
                        SuggestionChip(
                            onClick = { },
                            label = { Text(skill, fontWeight = FontWeight.SemiBold) },
                            shape = RoundedCornerShape(12.dp),
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = SkillColors[colorIndex].copy(alpha = 0.2f),
                                labelColor = SkillColors[colorIndex]
                            ),
                            border = SuggestionChipDefaults.suggestionChipBorder(
                                borderColor = SkillColors[colorIndex],
                                enabled = true,
                                borderWidth = 1.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(project: Project) {
    val context = LocalContext.current
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(DeepOcean, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(project.title.take(1), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = project.role,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftCyan
                    )
                }
                Icon(
                    if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.5f)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "🚀 ${project.technologies}",
                style = MaterialTheme.typography.bodySmall,
                color = VibrantAmber,
                fontWeight = FontWeight.Bold
            )
            
            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Key Contributions:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = SoftCyan
                )
                project.keyContributions.forEach { contribution ->
                    Row(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                        Text("•", color = VibrantAmber, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = contribution,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
                
                project.note?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = DeepOrange.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, DeepOrange.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "ℹ️ $it",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp),
                            fontStyle = FontStyle.Italic,
                            color = DeepOrange
                        )
                    }
                }
                
                project.link?.let { link ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("View Project")
                    }
                }
            }
        }
    }
}

@Composable
private fun ExperienceCard(experience: Experience) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(12.dp).background(VibrantAmber, CircleShape)
                )
                Box(
                    modifier = Modifier.width(2.dp).height(100.dp).background(VibrantAmber.copy(alpha = 0.3f))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
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
                            color = Color.White
                        )
                        Text(
                            text = experience.company,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SoftCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = experience.period,
                        style = MaterialTheme.typography.labelSmall,
                        color = VibrantAmber
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                experience.highlights.forEach { highlight ->
                    Row(modifier = Modifier.padding(bottom = 4.dp)) {
                        Text("▹", color = VibrantAmber)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}
