package com.juan.lazy.philipportfolio.data

import android.content.Context
import com.juan.lazy.philipportfolio.data.local.PortfolioDao
import com.juan.lazy.philipportfolio.data.local.PortfolioEntity
import com.juan.lazy.philipportfolio.model.EmploymentPeriod
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioData
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import com.juan.lazy.philipportfolio.model.SyncStatus
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface PortfolioRepository {
    fun getPortfolioData(): Flow<PortfolioUiState>
}

class NetworkPortfolioRepository(
    private val context: Context,
    private val portfolioDao: PortfolioDao
) : PortfolioRepository {
    
    private val json = Json { 
        ignoreUnknownKeys = true 
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().build())
        .build()

    private val apiService = retrofit.create(PortfolioApiService::class.java)

    override fun getPortfolioData(): Flow<PortfolioUiState> = flow {
        // First, check local database
        var localData = portfolioDao.getPortfolio()
        
        // If empty, load from assets and save to database
        if (localData == null) {
            try {
                val initialJson = context.assets.open("portfolio_initial.json").bufferedReader().use { it.readText() }
                localData = PortfolioEntity(jsonContent = initialJson)
                portfolioDao.insertPortfolio(localData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // If we have local data (either from DB or just loaded from assets), emit it
        var hasLocalData = false
        if (localData != null) {
            try {
                val portfolio = json.decodeFromString<PortfolioData>(localData.jsonContent)
                emit(mapToUiState(portfolio).copy(isLoading = false, syncStatus = SyncStatus.SYNCING))
                hasLocalData = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Then, try to fetch from network and update local storage
        try {
            val networkData = apiService.getPortfolio()
            val jsonString = json.encodeToString(networkData)
            portfolioDao.insertPortfolio(PortfolioEntity(jsonContent = jsonString))
            
            // Emit updated state with SUCCESS status
            emit(mapToUiState(networkData).copy(isLoading = false, syncStatus = SyncStatus.SUCCESS))
            
            // Wait a moment so the user can see the "Success/Synced" state
            delay(2000)
            
            // Finally return to IDLE
            emit(mapToUiState(networkData).copy(isLoading = false, syncStatus = SyncStatus.IDLE))
        } catch (e: Exception) {
            e.printStackTrace()
            if (hasLocalData) {
                val portfolio = json.decodeFromString<PortfolioData>(localData!!.jsonContent)
                emit(mapToUiState(portfolio).copy(isLoading = false, syncStatus = SyncStatus.ERROR))
                delay(2000)
                emit(mapToUiState(portfolio).copy(isLoading = false, syncStatus = SyncStatus.IDLE))
            } else {
                emit(PortfolioUiState(isLoading = false, syncStatus = SyncStatus.ERROR))
                delay(2000)
                emit(PortfolioUiState(isLoading = false, syncStatus = SyncStatus.IDLE))
            }
        }
    }

    private fun mapToUiState(data: PortfolioData): PortfolioUiState {
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
            projects = data.featuredProjects.map { proj ->
                Project(
                    title = proj.name,
                    role = proj.role,
                    technologies = proj.technologies.joinToString(", "),
                    description = proj.overview,
                    keyContributions = proj.keyContributions,
                    link = proj.projectUrl,
                    note = when {
                        proj.notes?.enterpriseOnly == true -> "Enterprise only application"
                        proj.notes?.playStoreAvailable == false -> "Not available on Play Store"
                        else -> null
                    }
                )
            },
            experiences = data.professionalExperience.map { exp ->
                Experience(
                    role = exp.position,
                    company = exp.company,
                    period = formatFullPeriod(exp.employmentPeriod),
                    highlights = exp.responsibilities
                )
            },
            education = data.education.firstOrNull()?.let { 
                "${it.degree} - ${it.institution} (${it.yearGraduated})" 
            } ?: "",
            languages = data.languages.map { "${it.language}: ${it.proficiency}" }
        )
    }

    private fun formatFullPeriod(period: EmploymentPeriod): String {
        val dateRange = "${formatDate(period.start)} – ${formatDate(period.end)}"
        val duration = calculateDuration(period.start, period.end)
        return if (duration.isNotEmpty()) "$dateRange • $duration" else dateRange
    }

    private fun formatDate(dateStr: String): String {
        if (dateStr.lowercase() == "present") return "Present"
        return try {
            val parts = dateStr.split("-")
            if (parts.size == 2) {
                val year = parts[0]
                val month = parts[1].toInt()
                val monthName = when (month) {
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
                if (monthName.isNotEmpty()) "$monthName $year" else dateStr
            } else {
                dateStr
            }
        } catch (e: Exception) {
            dateStr
        }
    }

    private fun calculateDuration(start: String, end: String): String {
        return try {
            val startParts = start.split("-")
            val endParts = if (end.lowercase() == "present" || end.isEmpty()) {
                val now = java.util.Calendar.getInstance()
                listOf(now.get(java.util.Calendar.YEAR).toString(), (now.get(java.util.Calendar.MONTH) + 1).toString())
            } else {
                end.split("-")
            }

            if (startParts.size >= 2 && endParts.size >= 2) {
                val startYear = startParts[0].toInt()
                val startMonth = startParts[1].toInt()
                val endYear = endParts[0].toInt()
                val endMonth = endParts[1].toInt()

                var totalMonths = (endYear - startYear) * 12 + (endMonth - startMonth) + 1
                
                if (totalMonths < 0) totalMonths = 0

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

                when {
                    yearStr.isNotEmpty() && monthStr.isNotEmpty() -> "$yearStr and $monthStr"
                    yearStr.isNotEmpty() -> yearStr
                    monthStr.isNotEmpty() -> monthStr
                    else -> "Less than a month"
                }
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }
}
