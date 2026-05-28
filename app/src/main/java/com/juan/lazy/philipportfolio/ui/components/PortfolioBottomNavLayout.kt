package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import kotlinx.coroutines.launch


@Composable
internal fun PortfolioBottomNavLayout(
    uiState: PortfolioUiState,
    onThemeSelected: (AppTheme) -> Unit
) {
    val tabs = listOf("About", "Skills", "Projects", "Experience", "Settings")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    var isExpanded by remember { mutableStateOf(true) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -5) {
                    isExpanded = false
                } else if (available.y > 5) {
                    isExpanded = true
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(uiState)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { page ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 120.dp
                    ),
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

        FloatingTabBar(
            tabs = tabs,
            selectedIndex = pagerState.currentPage,
            isExpanded = isExpanded,
            onTabSelected = { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
                isExpanded = true
            },
            modifier = Modifier
                .align(if (isExpanded) Alignment.BottomCenter
                else Alignment.BottomEnd)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )
    }
}