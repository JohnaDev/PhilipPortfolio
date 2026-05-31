package com.juan.lazy.philipportfolio.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector


fun getIconForTab(index: Int): ImageVector = when (index) {
    0 -> Icons.Filled.Person
    1 -> Icons.Filled.Build
    2 -> Icons.Filled.Star
    3 -> Icons.Filled.Work
    else -> Icons.Filled.Settings
}