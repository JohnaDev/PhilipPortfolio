package com.juan.lazy.philipportfolio.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    val profile: ProfileData,
    @SerialName("technical_skills")
    val technicalSkills: TechnicalSkills,
    @SerialName("featured_projects")
    val featuredProjects: List<ProjectData>,
    @SerialName("professional_experience")
    val professionalExperience: List<ExperienceData>,
    val education: List<EducationData>,
    val languages: List<LanguageProficiency>
)

@Serializable
data class ProfileData(
    @SerialName("full_name")
    val fullName: String,
    val title: String,
    val location: LocationData,
    val contact: ContactData,
    @SerialName("about_me")
    val aboutMe: String
)

@Serializable
data class LocationData(
    val city: String,
    val country: String
)

@Serializable
data class ContactData(
    val email: String,
    val phone: String
)

@Serializable
data class TechnicalSkills(
    val languages: List<String>,
    @SerialName("frameworks_and_tools")
    val frameworksAndTools: List<String>,
    @SerialName("project_management")
    val projectManagement: List<String>,
    @SerialName("build_and_version_control")
    val buildAndVersionControl: List<String>,
    @SerialName("core_concepts")
    val coreConcepts: List<String>
)

@Serializable
data class ProjectData(
    val name: String,
    val role: String,
    @SerialName("project_url")
    val projectUrl: String,
    val technologies: List<String>,
    val overview: String,
    val features: List<String> = emptyList(),
    @SerialName("key_contributions")
    val keyContributions: List<String>,
    val notes: ProjectNotes? = null
)

@Serializable
data class ProjectNotes(
    @SerialName("enterprise_only")
    val enterpriseOnly: Boolean? = null,
    @SerialName("play_store_available")
    val playStoreAvailable: Boolean? = null
)

@Serializable
data class ExperienceData(
    val position: String,
    val company: String,
    @SerialName("employment_period")
    val employmentPeriod: EmploymentPeriod,
    val responsibilities: List<String>
)

@Serializable
data class EmploymentPeriod(
    val start: String,
    val end: String
)

@Serializable
data class EducationData(
    val degree: String,
    val institution: String,
    @SerialName("year_graduated")
    val yearGraduated: Int
)

@Serializable
data class LanguageProficiency(
    val language: String,
    val proficiency: String
)

// UI Models
data class Project(
    val title: String,
    val role: String,
    val technologies: String,
    val description: String,
    val keyContributions: List<String>,
    val link: String? = null,
    val note: String? = null
)

data class Experience(
    val role: String,
    val company: String,
    val period: String,
    val highlights: List<String>
)

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

data class PortfolioUiState(
    val isLoading: Boolean = true,
    val selectedTheme: AppTheme = AppTheme.SYSTEM,
    val name: String = "",
    val role: String = "",
    val location: String = "",
    val email: String = "",
    val phone: String = "",
    val aboutMe: String = "",
    val skills: Map<String, List<String>> = emptyMap(),
    val projects: List<Project> = emptyList(),
    val experiences: List<Experience> = emptyList(),
    val education: String = "",
    val languages: List<String> = emptyList()
)
