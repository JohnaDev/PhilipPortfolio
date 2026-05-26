package com.juan.lazy.philipportfolio.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.R
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import com.juan.lazy.philipportfolio.model.SyncStatus
import com.juan.lazy.philipportfolio.ui.components.AboutMeSection
import com.juan.lazy.philipportfolio.ui.components.EducationSection
import com.juan.lazy.philipportfolio.ui.components.ExperienceCard
import com.juan.lazy.philipportfolio.ui.components.HeaderSection
import com.juan.lazy.philipportfolio.ui.components.LanguagesSection
import com.juan.lazy.philipportfolio.ui.components.ProjectCard
import com.juan.lazy.philipportfolio.ui.components.SectionHeader
import com.juan.lazy.philipportfolio.ui.components.SkillsSection
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme
import kotlinx.coroutines.launch

@Composable
fun PortfolioScreen(
    uiState: PortfolioUiState,
    windowSize: WindowSizeClass,
    onThemeSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PortfolioTheme.colors.accentPrimary)
        }
    } else {
        val gradientBackground = Brush.verticalGradient(
            colors = listOf(PortfolioTheme.colors.background, PortfolioTheme.colors.backgroundGradientEnd)
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                PortfolioBottomNavLayout(uiState, onThemeSelected)
            } else {
                PortfolioExpandedTabbedLayout(uiState, onThemeSelected)
            }

            SyncingIndicator(uiState.syncStatus)
        }
    }
}

@Composable
private fun SyncingIndicator(syncStatus: SyncStatus) {
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
                            imageVector = icon as androidx.compose.ui.graphics.vector.ImageVector,
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

@Composable
internal fun PortfolioBottomNavLayout(
    uiState: PortfolioUiState,
    onThemeSelected: (AppTheme) -> Unit
) {
    val tabs = listOf("About", "Skills", "Projects", "Experience", "Settings")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            HeaderSection(uiState)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { page ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (page) {
                        0 -> { // About + Education + Languages
                            item { SectionHeader("About Me", Icons.Filled.Person) }
                            item { AboutMeSection(uiState.aboutMe) }
                            item { SectionHeader("Education", Icons.AutoMirrored.Filled.MenuBook) }
                            item { EducationSection(uiState.education) }
                            item { SectionHeader("Languages", Icons.Filled.Language) }
                            item { LanguagesSection(uiState.languages) }
                        }
                        1 -> {
                            item { SkillsSection(uiState.skills) }
                        }
                        2 -> {
                            item { SectionHeader("Featured Projects", Icons.Filled.Star) }
                            items(uiState.projects) { project ->
                                ProjectCard(project)
                            }
                        }
                        3 -> {
                            items(uiState.experiences) { experience ->
                                ExperienceCard(experience)
                            }
                        }
                        4 -> { // Settings
                            item { SectionHeader("Theme", Icons.Filled.Settings) }
                            item { ThemeSelectionSection(uiState.selectedTheme, onThemeSelected) }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }

        NavigationBar(
            containerColor = PortfolioTheme.colors.background,
            contentColor = PortfolioTheme.colors.accentPrimary,
            tonalElevation = 8.dp
        ) {
            tabs.forEachIndexed { index, title ->
                NavigationBarItem(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    icon = {
                        val icon = when (index) {
                            0 -> Icons.Filled.Person
                            1 -> Icons.Filled.Build
                            2 -> Icons.Filled.Star
                            3 -> Icons.Filled.Work
                            else -> Icons.Filled.Settings
                        }
                        Icon(icon, contentDescription = title)
                    },
                    label = { 
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall
                        ) 
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PortfolioTheme.colors.accentPrimary,
                        selectedTextColor = PortfolioTheme.colors.accentPrimary,
                        unselectedIconColor = PortfolioTheme.colors.textSecondary,
                        unselectedTextColor = PortfolioTheme.colors.textSecondary,
                        indicatorColor = PortfolioTheme.colors.accentPrimary.copy(alpha = 0.1f)
                    )
                )
            }
        }
    }
}

@Composable
internal fun PortfolioExpandedTabbedLayout(
    uiState: PortfolioUiState,
    onThemeSelected: (AppTheme) -> Unit
) {
    val tabs = listOf("About", "Skills", "Projects", "Experience", "Settings")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail(
            containerColor = Color.Transparent,
            header = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(PortfolioTheme.colors.accentPrimary, PortfolioTheme.colors.accentSecondary)),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                            .background(PortfolioTheme.colors.background, CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_photo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                NavigationRailItem(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    icon = {
                        val icon = when (index) {
                            0 -> Icons.Filled.Person
                            1 -> Icons.Filled.Build
                            2 -> Icons.Filled.Star
                            3 -> Icons.Filled.Work
                            else -> Icons.Filled.Settings
                        }
                        Icon(icon, contentDescription = title)
                    },
                    label = { Text(title) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = PortfolioTheme.colors.accentPrimary,
                        selectedTextColor = PortfolioTheme.colors.accentPrimary,
                        unselectedIconColor = PortfolioTheme.colors.textSecondary,
                        unselectedTextColor = PortfolioTheme.colors.textSecondary,
                        indicatorColor = PortfolioTheme.colors.accentPrimary.copy(alpha = 0.1f)
                    )
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                userScrollEnabled = false
            ) { page ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    when (page) {
                        0 -> {
                            item { SectionHeader("🧭 About Me", Icons.Filled.Person) }
                            item { AboutMeSection(uiState.aboutMe) }
                            item { ContactInfoRow(uiState) }
                            item { SectionHeader("🎓 Education", Icons.AutoMirrored.Filled.MenuBook) }
                            item { EducationSection(uiState.education) }
                            item { SectionHeader("🌐 Languages", Icons.Filled.Language) }
                            item { LanguagesSection(uiState.languages) }
                        }
                        1 -> {
                            item { SectionHeader("🛠️ Technical Skills", Icons.Filled.Build) }
                            item { SkillsSection(uiState.skills) }
                        }
                        2 -> {
                            item { SectionHeader("📱 Featured Projects", Icons.Filled.Star) }
                            items(uiState.projects) { project ->
                                ProjectCard(project)
                            }
                        }
                        3 -> {
                            item { SectionHeader("💼 Professional Experience", Icons.Filled.Work) }
                            items(uiState.experiences) { experience ->
                                ExperienceCard(experience)
                            }
                        }
                        4 -> {
                            item { SectionHeader("⚙️ Settings", Icons.Filled.Settings) }
                            item { ThemeSelectionSection(uiState.selectedTheme, onThemeSelected) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSelectionSection(
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Surface(
        color = PortfolioTheme.colors.cardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, PortfolioTheme.colors.textSecondary.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AppTheme.entries.forEach { theme ->
                val isSelected = currentTheme == theme
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) 
                                PortfolioTheme.colors.accentPrimary 
                            else 
                                PortfolioTheme.colors.textSecondary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onThemeSelected(theme) }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onThemeSelected(theme) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PortfolioTheme.colors.accentPrimary,
                            unselectedColor = PortfolioTheme.colors.textSecondary.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = theme.name.lowercase().replaceFirstChar { it.uppercase() },
                        color = if (isSelected) PortfolioTheme.colors.accentPrimary else PortfolioTheme.colors.textPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactInfoRow(uiState: PortfolioUiState) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(uiState.email, color = PortfolioTheme.colors.textSecondary, style = MaterialTheme.typography.bodyMedium)
            Text(uiState.phone, color = PortfolioTheme.colors.textSecondary, style = MaterialTheme.typography.bodyMedium)
            Text(uiState.location, color = PortfolioTheme.colors.textSecondary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private val PreviewUiState = PortfolioUiState(
    isLoading = false,
    name = "John Philip Agustino",
    role = "Android Developer",
    location = "Davao City, Philippines",
    email = "johnphilipagustino@gmail.com",
    phone = "+63 9399353004",
    aboutMe = "Experienced Android Developer with a strong background in building and maintaining native Android applications.",
    skills = mapOf(
        "Languages" to listOf("Kotlin", "Java", "Lua"),
        "Frameworks" to listOf("Jetpack Compose", "Firebase", "Retrofit")
    ),
    projects = listOf(
        Project(
            title = "Little Ones",
            role = "Senior Android Developer",
            technologies = "Kotlin, Jetpack Compose, Firebase",
            description = "Baby sleep and parenting app.",
            keyContributions = listOf("Rebuilt major app components", "Developed social feed"),
            link = "https://play.google.com/store/apps/details?id=nz.co.littleones.prod"
        )
    ),
    experiences = listOf(
        Experience(
            role = "Senior Android Developer",
            company = "Dev Partners",
            period = "2018 – Present",
            highlights = listOf("Modernized apps with Jetpack Compose", "Mentored junior developers")
        )
    ),
    education = "Bachelor of Science in Computer Science - Notre Dame University 2012",
    languages = listOf("English: Fluent", "Filipino: Fluent")
)

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PortfolioCompactPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(PortfolioTheme.colors.background)) {
            PortfolioBottomNavLayout(PreviewUiState, onThemeSelected = {})
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
fun PortfolioExpandedPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(PortfolioTheme.colors.background)) {
            PortfolioExpandedTabbedLayout(PreviewUiState, onThemeSelected = {})
        }
    }
}
