package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.*
import kotlinx.datetime.*

object PortfolioMapper {

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
            val date = parseYearMonth(dateStr)
            "${getMonthName(date.monthNumber)} ${date.year}"
        } catch (e: Exception) {
            dateStr
        }
    }

    private fun calculateDuration(startStr: String, endStr: String): String {
        return try {
            val start = parseYearMonth(startStr)
            val end = if (endStr.equals("present", ignoreCase = true)) {
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            } else {
                parseYearMonth(endStr)
            }

            val totalMonths = (end.year - start.year) * 12 + (end.monthNumber - start.monthNumber) + 1
            val years = totalMonths / 12
            val months = totalMonths % 12

            val yearStr = when {
                years == 1 -> "1 year"
                years > 1 -> "$years years"
                else -> ""
            }

            val monthStr = when {
                months == 1 -> "1 month"
                months > 1 -> "$months months"
                else -> ""
            }

            listOf(yearStr, monthStr).filter { it.isNotEmpty() }.joinToString(" and ")
        } catch (e: Exception) {
            ""
        }
    }

    private fun parseYearMonth(dateStr: String): LocalDate {
        // Expected format "yyyy-MM"
        val parts = dateStr.split("-")
        return LocalDate(parts[0].toInt(), parts[1].toInt(), 1)
    }

    private fun getMonthName(month: Int): String = when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}
