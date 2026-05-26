package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.Experience
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PortfolioRepository {
    fun getPortfolioData(): Flow<PortfolioUiState>
}

class FakePortfolioRepository : PortfolioRepository {
    override fun getPortfolioData(): Flow<PortfolioUiState> = flow {
        emit(
            PortfolioUiState(
                name = "John Philip Agustino",
                role = "Android Developer",
                location = "Davao City, Philippines",
                email = "johnphilipagustino@gmail.com",
                phone = "+63 9399353004",
                aboutMe = "Experienced Android Developer with a strong background in building and maintaining native Android applications and mobile games. Skilled in Kotlin, Java, Jetpack Compose, and modern Android frameworks. Passionate about crafting high quality, maintainable, and user centered apps from healthcare solutions to mobile games.",
                skills = mapOf(
                    "Languages" to listOf("Kotlin", "Java", "Lua"),
                    "Frameworks & Tools" to listOf("Android SDK", "Jetpack Compose", "Firebase", "Retrofit", "Glide", "MVVM"),
                    "Project Management" to listOf("Jira", "Trello", "OpenProject"),
                    "Build & Version Control" to listOf("Gradle", "Git", "GitHub"),
                    "Core Concepts" to listOf("RESTful APIs", "CI/CD", "Material Design", "Unit Testing (JUnit, Espresso)")
                ),
                projects = listOf(
                    Project(
                        title = "Little Ones",
                        role = "Senior Android Developer",
                        technologies = "Kotlin, Java, xml, Jetpack Compose, Firebase, Retrofit",
                        description = "Little Ones is a baby sleep and parenting app that provides expert routines, sleep tracking, and personalized advice.",
                        keyContributions = listOf(
                            "Rebuilt major app components using Jetpack Compose and a modular architecture.",
                            "Developed a social feed supporting posts, comments, and media uploads with real-time updates.",
                            "Implemented a private chat and messaging system using Firebase Firestore, Realtime Database, and Firebase Authentication.",
                            "Integrated Cloud Messaging for notifications and engagement.",
                            "Collaborated with product and UX teams to ensure a seamless user experience that aligns with Material Design 3."
                        ),
                        link = "https://play.google.com/store/apps/details?id=nz.co.littleones.prod"
                    ),
                    Project(
                        title = "F45 Training",
                        role = "Android Developer",
                        technologies = "Kotlin, Android TV SDK, Bluetooth, Restful api",
                        description = "F45 Training is a global fitness brand offering functional training programs. This Android TV application acts as a virtual workout coach.",
                        keyContributions = listOf(
                            "Built Android TV apps that display real time workout sessions on large studio screens.",
                            "Integrated Bluetooth heart rate monitors for live tracking and performance visualization.",
                            "Implemented real time workout synchronization and interactive session displays.",
                            "Optimized playback and rendering for high resolution video and continuous operation.",
                            "Ensured reliable multi screen synchronization across studio environments."
                        ),
                        note = "Enterprise only application developed for F45 studios and internal use - not available on the Google Play Store."
                    ),
                    Project(
                        title = "Perfect Home Health",
                        role = "Android Developer",
                        technologies = "Java, xml, Retrofit, SQLite, JSON",
                        description = "A healthcare management app used by home health professionals for scheduling, documentation, and compliance tracking.",
                        keyContributions = listOf(
                            "Built modules for patient data entry, scheduling, and synchronization.",
                            "Implemented secure RESTful API integration.",
                            "Ensured HIPAA compliance and reliability for field operations."
                        ),
                        link = "https://play.google.com/store/apps/details?id=com.noteefied.perfect"
                    ),
                    Project(
                        title = "Eagle Quote",
                        role = "Senior Android Developer",
                        technologies = "Java, xml, Kotlin, MVVM, Retrofit",
                        description = "A quoting and CRM tool for insurance agents to generate and manage client quotes efficiently.",
                        keyContributions = listOf(
                            "Developed MVVM based modules for scalable architecture.",
                            "Integrated RESTful APIs for quote generation and management.",
                            "Improved testing and deployment workflows with CI/CD pipelines."
                        ),
                        link = "https://play.google.com/store/apps/details?id=blackfintechnology.eaglequote"
                    ),
                    Project(
                        title = "Tilt Toby",
                        role = "Lead Game Developer",
                        technologies = "Lua, Corona SDK, Android SDK",
                        description = "A tilt based physics mobile game featuring fun mechanics and smooth motion controls.",
                        keyContributions = listOf(
                            "Designed and built the game using Corona SDK (Solar2D) and Lua scripting.",
                            "Implemented responsive tilt controls and optimized physics rendering.",
                            "Directed a small team and managed full release cycle, from development to QA."
                        ),
                        link = "https://www.amazon.com/Larry-Hall-Machado-Tilt-Toby-Lite/dp/B00GXZSJ1S"
                    )
                ),
                experiences = listOf(
                    Experience(
                        role = "Senior Android Developer",
                        company = "Dev Partners",
                        period = "2018 – Present",
                        highlights = listOf(
                            "Modernized apps with Jetpack Compose and improved CI/CD efficiency.",
                            "Mentored junior developers on architecture and testing best practices."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Bywave",
                        period = "2015 – 2018",
                        highlights = listOf(
                            "Integrated Bluetooth heart rate monitor connectivity and REST APIs.",
                            "Implemented Material Design UIs and SQLite data handling.",
                            "Built Android TV apps for F45 Training with Bluetooth device integration."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Note-e-fied Inc.",
                        period = "2014 – 2015",
                        highlights = listOf(
                            "Built healthcare focused Android apps that follows HIPAA compliance."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Data Soft Logic",
                        period = "2014",
                        highlights = listOf(
                            "Developed secure, enterprise grade Android apps with API integration."
                        )
                    ),
                    Experience(
                        role = "Lead Mobile Game Developer",
                        company = "8appstudio",
                        period = "2012 – 2013",
                        highlights = listOf(
                            "Led mobile game projects using Corona SDK and Android SDK."
                        )
                    )
                ),
                education = "Bachelor of Science in Computer Science - Notre Dame University of Cotabato 2012",
                languages = listOf("English: Fluent", "Filipino: Fluent")
            )
        )
    }
}
