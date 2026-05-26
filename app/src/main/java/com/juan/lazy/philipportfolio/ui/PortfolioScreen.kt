package com.juan.lazy.philipportfolio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import com.juan.lazy.philipportfolio.ui.components.AboutMeSection
import com.juan.lazy.philipportfolio.ui.components.EducationSection
import com.juan.lazy.philipportfolio.ui.components.ExperienceCard
import com.juan.lazy.philipportfolio.ui.components.HeaderSection
import com.juan.lazy.philipportfolio.ui.components.LanguagesSection
import com.juan.lazy.philipportfolio.ui.components.ProjectCard
import com.juan.lazy.philipportfolio.ui.components.SectionHeader
import com.juan.lazy.philipportfolio.ui.components.SkillsSection
import com.juan.lazy.philipportfolio.ui.theme.DarkBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme

@Composable
fun PortfolioScreen(
    uiState: PortfolioUiState,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(DarkBackground, Color.Black)
    )

    Box(modifier = modifier
        .fillMaxSize()
        .background(gradientBackground)) {
        
        if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
            PortfolioCompactLayout(uiState)
        } else {
            PortfolioExpandedLayout(uiState)
        }
    }
}

@Composable
internal fun PortfolioCompactLayout(uiState: PortfolioUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { HeaderSection(uiState) }
        
        item { SectionHeader("🧭 About Me", Icons.Filled.Person) }
        item { AboutMeSection(uiState.aboutMe) }
        
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
        item { EducationSection(uiState.education) }

        item { SectionHeader("🌐 Languages", Icons.Filled.Language) }
        item { LanguagesSection(uiState.languages) }
        
        item { Spacer(modifier = Modifier.height(48.dp)) }
    }
}

@Composable
internal fun PortfolioExpandedLayout(uiState: PortfolioUiState) {
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left Column: Profile, Skills, Education, Languages
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HeaderSection(uiState) }
            
            item { SectionHeader("🧭 About Me", Icons.Filled.Person) }
            item { AboutMeSection(uiState.aboutMe) }

            item { SectionHeader("🛠️ Technical Skills", Icons.Filled.Build) }
            item { SkillsSection(uiState.skills) }

            item { SectionHeader("🎓 Education", Icons.AutoMirrored.Filled.MenuBook) }
            item { EducationSection(uiState.education) }

            item { SectionHeader("🌐 Languages", Icons.Filled.Language) }
            item { LanguagesSection(uiState.languages) }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }

        // Right Column: Projects and Experience
        LazyColumn(
            modifier = Modifier.weight(1.5f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { SectionHeader("📱 Featured Projects", Icons.Filled.Star) }
            itemsIndexed(uiState.projects) { _, project ->
                ProjectCard(project)
            }
            
            item { SectionHeader("💼 Professional Experience", Icons.Filled.Work) }
            itemsIndexed(uiState.experiences) { _, experience ->
                ExperienceCard(experience)
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

private val PreviewUiState = PortfolioUiState(
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
        Box(modifier = Modifier.background(DarkBackground)) {
            PortfolioCompactLayout(PreviewUiState)
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
fun PortfolioExpandedPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(DarkBackground)) {
            PortfolioExpandedLayout(PreviewUiState)
        }
    }
}
