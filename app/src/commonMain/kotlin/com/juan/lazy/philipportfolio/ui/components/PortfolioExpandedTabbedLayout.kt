package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.getIconForTab
import com.juan.lazy.philipportfolio.ui.theme.PortfolioTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import philipportfolio.app.generated.resources.Res
import philipportfolio.app.generated.resources.profile_photo


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
                                brush = Brush.linearGradient(
                                    listOf(
                                        PortfolioTheme.colors.accentPrimary,
                                        PortfolioTheme.colors.accentSecondary
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                            .background(PortfolioTheme.colors.background, CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.profile_photo),
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
                        Icon(getIconForTab(index), contentDescription = title)
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
                            item { SectionHeader("About Me", Icons.Filled.Person) }
                            item { AboutMeSection(uiState.aboutMe) }
                            item { ContactInfoRow(uiState) }
                            item {
                                SectionHeader(
                                    "Education",
                                    Icons.AutoMirrored.Filled.MenuBook
                                )
                            }
                            item { EducationSection(uiState.education) }
                            item { SectionHeader("Languages", Icons.Filled.Language) }
                            item { LanguagesSection(uiState.languages) }
                        }

                        1 -> {
                            item { SectionHeader("Technical Skills", Icons.Filled.Build) }
                            item { SkillsSection(uiState.skills) }
                        }

                        2 -> {
                            item { SectionHeader("Featured Projects", Icons.Filled.Star) }
                            items(uiState.projects) { project ->
                                ProjectCard(project)
                            }
                        }

                        3 -> {
                            item { SectionHeader("Professional Experience", Icons.Filled.Work) }
                            items(uiState.experiences) { experience ->
                                ExperienceCard(experience)
                            }
                        }

                        4 -> {
                            item { SectionHeader("Settings", Icons.Filled.Settings) }
                            item { ThemeSelectionSection(uiState.selectedTheme, onThemeSelected) }
                        }
                    }
                }
            }
        }
    }
}
