package com.juan.lazy.philipportfolio.data

import android.content.Context
import com.juan.lazy.philipportfolio.data.local.PortfolioDao
import com.juan.lazy.philipportfolio.data.local.PortfolioEntity
import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioData
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
        if (localData != null) {
            try {
                val portfolio = json.decodeFromString<PortfolioData>(localData.jsonContent)
                emit(mapToUiState(portfolio).copy(isLoading = false))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Then, try to fetch from network and update local storage
        try {
            val networkData = apiService.getPortfolio()
            val jsonString = json.encodeToString(networkData)
            portfolioDao.insertPortfolio(PortfolioEntity(jsonContent = jsonString))
            
            // Emit updated state
            emit(mapToUiState(networkData).copy(isLoading = false))
        } catch (e: Exception) {
            e.printStackTrace()
            // Stop loading if network fails and we didn't have any data
            if (localData == null) {
                emit(PortfolioUiState(isLoading = false))
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
                    period = "${exp.employmentPeriod.start} – ${exp.employmentPeriod.end}",
                    highlights = exp.responsibilities
                )
            },
            education = data.education.firstOrNull()?.let { 
                "${it.degree} - ${it.institution} (${it.yearGraduated})" 
            } ?: "",
            languages = data.languages.map { "${it.language}: ${it.proficiency}" }
        )
    }
}
