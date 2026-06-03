package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioMapperTest {

    @Test
    fun `mapToUiState maps PortfolioData correctly`() {
        // Given
        val portfolioData = PortfolioData(
            profile = ProfileData(
                fullName = "John Doe",
                title = "Senior Developer",
                location = LocationData("New York", "USA"),
                contact = ContactData("john@example.com", "123456789"),
                aboutMe = "Developer profile"
            ),
            technicalSkills = TechnicalSkills(
                languages = listOf("Kotlin", "Java"),
                frameworksAndTools = listOf("Android SDK", "Jetpack Compose"),
                projectManagement = listOf("Agile"),
                buildAndVersionControl = listOf("Git", "Gradle"),
                coreConcepts = listOf("MVVM", "Clean Architecture")
            ),
            featuredProjects = listOf(
                ProjectData(
                    name = "Project A",
                    role = "Lead Developer",
                    projectUrl = "https://example.com/a",
                    technologies = listOf("Kotlin", "Compose"),
                    overview = "Awesome project",
                    keyContributions = listOf("Initial setup", "Feature X")
                )
            ),
            professionalExperience = listOf(
                ExperienceData(
                    position = "Developer",
                    company = "Tech Corp",
                    employmentPeriod = EmploymentPeriod("2020-01", "2021-12"),
                    responsibilities = listOf("Coding", "Reviewing")
                )
            ),
            education = listOf(
                EducationData("B.S. Computer Science", "State University", 2019)
            ),
            languages = listOf(
                LanguageProficiency("English", "Native")
            )
        )

        // When
        val uiState = PortfolioMapper.mapToUiState(portfolioData)

        // Then
        assertEquals("John Doe", uiState.name)
        assertEquals("Senior Developer", uiState.role)
        assertEquals("New York, USA", uiState.location)
        assertEquals("john@example.com", uiState.email)
        assertEquals("123456789", uiState.phone)
        assertEquals("Developer profile", uiState.aboutMe)
        
        assertEquals(2, uiState.skills["Languages"]?.size)
        assertEquals("Kotlin", uiState.skills["Languages"]?.get(0))
        
        assertEquals(1, uiState.projects.size)
        assertEquals("Project A", uiState.projects[0].title)
        assertEquals("Kotlin, Compose", uiState.projects[0].technologies)
        
        assertEquals(1, uiState.experiences.size)
        assertEquals("Developer", uiState.experiences[0].role)
        assertEquals("Tech Corp", uiState.experiences[0].company)
        // Check formatted period
        assertEquals("January 2020 – December 2021 • 2 years", uiState.experiences[0].period)
        
        assertEquals("B.S. Computer Science - State University (2019)", uiState.education)
        assertEquals("English: Native", uiState.languages[0])
    }

    @Test
    fun `mapToUiState handles project notes correctly`() {
        // Given
        val projectWithNote = ProjectData(
            name = "Enterprise App",
            role = "Dev",
            projectUrl = "",
            technologies = emptyList(),
            overview = "",
            keyContributions = emptyList(),
            notes = ProjectNotes(enterpriseOnly = true)
        )
        val data = PortfolioData(
            profile = ProfileData("", "", LocationData("", ""), ContactData("", ""), ""),
            technicalSkills = TechnicalSkills(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()),
            featuredProjects = listOf(projectWithNote),
            professionalExperience = emptyList(),
            education = emptyList(),
            languages = emptyList()
        )

        // When
        val uiState = PortfolioMapper.mapToUiState(data)

        // Then
        assertEquals("Enterprise only application", uiState.projects[0].note)
    }
}
