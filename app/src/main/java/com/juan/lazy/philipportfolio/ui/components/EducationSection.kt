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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juan.lazy.philipportfolio.ui.theme.AccentTertiary
import com.juan.lazy.philipportfolio.ui.theme.CardBackground
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme

@Composable
fun EducationSection(education: String) {
    Surface(
        color = CardBackground,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, AccentTertiary.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = education,
            style = MaterialTheme.typography.bodyLarge,
            color = AccentTertiary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EducationSectionPreview() {
    PhilipPortfolioTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(16.dp)) {
            EducationSection(
                education = "Bachelor of Science in Computer Science - Notre Dame University 2012"
            )
        }
    }
}
