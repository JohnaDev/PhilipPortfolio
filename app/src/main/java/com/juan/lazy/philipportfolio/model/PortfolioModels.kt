package com.juan.lazy.philipportfolio.model

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

data class PortfolioUiState(
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
