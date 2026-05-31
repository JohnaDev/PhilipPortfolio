package com.juan.lazy.philipportfolio.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.SyncStatus
import com.juan.lazy.philipportfolio.model.getIconForTab
import com.juan.lazy.philipportfolio.ui.components.AboutMeSection
import com.juan.lazy.philipportfolio.ui.components.EducationSection
import com.juan.lazy.philipportfolio.ui.components.ExperienceCard
import com.juan.lazy.philipportfolio.ui.components.FloatingTabBar
import com.juan.lazy.philipportfolio.ui.components.HeaderSection
import com.juan.lazy.philipportfolio.ui.components.LanguagesSection
import com.juan.lazy.philipportfolio.ui.components.PortfolioBottomNavLayout
import com.juan.lazy.philipportfolio.ui.components.PortfolioExpandedTabbedLayout
import com.juan.lazy.philipportfolio.ui.components.ProjectCard
import com.juan.lazy.philipportfolio.ui.components.SectionHeader
import com.juan.lazy.philipportfolio.ui.components.SkillsSection
import com.juan.lazy.philipportfolio.ui.components.SyncingIndicator
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
            colors = listOf(
                PortfolioTheme.colors.background,
                PortfolioTheme.colors.backgroundGradientEnd
            )
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

