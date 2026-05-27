package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.*
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object PortfolioMapper {
    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)

    fun mapToUiState(data: PortfolioData): PortfolioUiState {
        val profile = data.profile
        return PortfolioUiState(
            isLoading = false,
            name = profile.fullName,
            role = profile.title,
            location = "${profile.location.city}, ${profile.location.country}",
            email = profile.contact.email,
            phone = profile.contact.phone,
            aboutMe = profile.aboutMe,
            skills = mapOf(
                "Languages" to data.technicalSkills.languages,
                "Frameworks & Tools" to data.technicalSkills.frameworksAndTools,
                "Project Management" to data.technicalSkills.projectManagement,
                "Build & Version Control" to data.technicalSkills.buildAndVersionControl,
                "Core Concepts" to data.technicalSkills.coreConcepts
            ),
            projects = data.featuredProjects.map { it.toUiModel() },
            experiences = data.professionalExperience.map { it.toUiModel() },
            education = data.education.firstOrNull()?.let { 
                "${it.degree} - ${it.institution} (${it.yearGraduated})" 
            } ?: "",
            languages = data.languages.map { "${it.language}: ${it.proficiency}" }
        )
    }

    private fun ProjectData.toUiModel() = Project(
        title = name,
        role = role,
        technologies = technologies.joinToString(", "),
        description = overview,
        keyContributions = keyContributions,
        link = projectUrl,
        note = when {
            notes?.enterpriseOnly == true -> "Enterprise only application"
            notes?.playStoreAvailable == false -> "Not available on Play Store"
            else -> null
        }
    )

    private fun ExperienceData.toUiModel() = Experience(
        role = position,
        company = company,
        period = formatFullPeriod(employmentPeriod),
        highlights = responsibilities
    )

    private fun formatFullPeriod(period: EmploymentPeriod): String {
        val startFormatted = formatDate(period.start)
        val endFormatted = formatDate(period.end)
        val dateRange = "$startFormatted – $endFormatted"
        
        val duration = calculateDuration(period.start, period.end)
        return if (duration.isNotEmpty()) "$dateRange • $duration" else dateRange
    }

    private fun formatDate(dateStr: String): String {
        if (dateStr.equals("present", ignoreCase = true)) return "Present"
        return try {
            YearMonth.parse(dateStr).format(monthFormatter)
        } catch (e: Exception) {
            dateStr
        }
    }

    private fun calculateDuration(startStr: String, endStr: String): String {
        return try {
            val start = YearMonth.parse(startStr)
            val end = if (endStr.equals("present", ignoreCase = true)) {
                YearMonth.now()
            } else {
                YearMonth.parse(endStr)
            }

            val totalMonths = ChronoUnit.MONTHS.between(start, end) + 1
            val years = totalMonths / 12
            val months = totalMonths % 12

            val yearStr = when {
                years == 1L -> "1 year"
                years > 1L -> "$years years"
                else -> ""
            }

            val monthStr = when {
                months == 1L -> "1 month"
                months > 1L -> "$months months"
                else -> ""
            }

            listOf(yearStr, monthStr).filter { it.isNotEmpty() }.joinToString(" and ")
        } catch (e: Exception) {
            ""
        }
    }
}
