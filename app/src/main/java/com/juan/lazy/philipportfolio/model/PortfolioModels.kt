package com.juan.lazy.philipportfolio.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioData(
    @SerialName("profile")
    val profile: ProfileData,
    @SerialName("technical_skills")
    val technicalSkills: TechnicalSkills,
    @SerialName("featured_projects")
    val featuredProjects: List<ProjectData>,
    @SerialName("professional_experience")
    val professionalExperience: List<ExperienceData>,
    @SerialName("education")
    val education: List<EducationData>,
    @SerialName("languages")
    val languages: List<LanguageProficiency>
)

@Serializable
data class ProfileData(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("title")
    val title: String,
    @SerialName("location")
    val location: LocationData,
    @SerialName("contact")
    val contact: ContactData,
    @SerialName("about_me")
    val aboutMe: String
)

@Serializable
data class LocationData(
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String
)

@Serializable
data class ContactData(
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String
)

@Serializable
data class TechnicalSkills(
    @SerialName("languages")
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
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("project_url")
    val projectUrl: String,
    @SerialName("technologies")
    val technologies: List<String>,
    @SerialName("overview")
    val overview: String,
    @SerialName("features")
    val features: List<String> = emptyList(),
    @SerialName("key_contributions")
    val keyContributions: List<String>,
    @SerialName("notes")
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
    @SerialName("position")
    val position: String,
    @SerialName("company")
    val company: String,
    @SerialName("employment_period")
    val employmentPeriod: EmploymentPeriod,
    @SerialName("responsibilities")
    val responsibilities: List<String>
)

@Serializable
data class EmploymentPeriod(
    @SerialName("start")
    val start: String,
    @SerialName("end")
    val end: String
)

@Serializable
data class EducationData(
    @SerialName("degree")
    val degree: String,
    @SerialName("institution")
    val institution: String,
    @SerialName("year_graduated")
    val yearGraduated: Int
)

@Serializable
data class LanguageProficiency(
    @SerialName("language")
    val language: String,
    @SerialName("proficiency")
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

enum class SyncStatus {
    IDLE, SYNCING, SUCCESS, ERROR
}

data class PortfolioUiState(
    val isLoading: Boolean = true,
    val syncStatus: SyncStatus = SyncStatus.IDLE,
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
