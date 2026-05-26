package com.juan.lazy.philipportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.lazy.philipportfolio.ui.theme.CardBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme
import com.juan.lazy.philipportfolio.ui.theme.TextPrimary

@Composable
fun AboutMeSection(aboutMe: String) {
    Surface(
        color = CardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            aboutMe,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary.copy(alpha = 0.9f),
            modifier = Modifier.padding(24.dp),
            lineHeight = 28.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutMeSectionPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(16.dp)) {
            AboutMeSection(
                aboutMe = "Experienced Android Developer with a strong background in building and maintaining native Android applications."
            )
        }
    }
}
