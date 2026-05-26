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
                        role = "Android Developer",
                        company = "Feiwin",
                        period = "December 2025 – May 2026",
                        highlights = listOf(
                            "Built and maintained the frontend of web based gaming and chat applications using Kotlin and Jetpack Compose.",
                            "Leveraged Ktor for networking, GitLab CI/CD for automated deployment pipelines, and Fastlane for build and release automation."
                        )
                    ),
                    Experience(
                        role = "Senior Android Developer",
                        company = "Dev Partners",
                        period = "June 2018 – December 2025",
                        highlights = listOf(
                            "Developed and maintained Android applications using Kotlin, Java, and XML, modernizing legacy code with Jetpack Compose.",
                            "Integrated Firebase for analytics, authentication, and cloud-based data synchronization.",
                            "Refactored and optimized codebases for performance, scalability, and maintainability.",
                            "Implemented CI/CD pipelines with Git and GitHub Actions for efficient testing and deployment.",
                            "Mentored team members and advocated for clean architecture and best coding practices."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Bywave",
                        period = "May 2015 – June 2018",
                        highlights = listOf(
                            "Developed Android applications primarily in Java using the Android SDK and XML-based UI layouts.",
                            "Implemented Bluetooth connectivity to pair with heart rate monitors for real-time health tracking.",
                            "Integrated RESTful APIs, SQLite, and JSON for data storage and communication.",
                            "Applied Material Design standards for improved usability and visual consistency.",
                            "Mentored junior developers, providing guidance on coding standards, version control, and debugging.",
                            "Conducted app performance tuning and implemented unit testing to enhance reliability."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Note-e-fied Incorporated",
                        period = "July 2014 – September 2015",
                        highlights = listOf(
                            "Developed Android applications for healthcare and hospice management, supporting home visits and patient documentation.",
                            "Focused on data security, offline functionality, and synchronization for reliable field use.",
                            "Collaborated with product and QA teams to ensure compliance with healthcare requirements and user experience goals."
                        )
                    ),
                    Experience(
                        role = "Android Developer",
                        company = "Data Soft Logic",
                        period = "January 2014 – May 2014",
                        highlights = listOf(
                            "Developed Android applications for enterprise and healthcare solutions, focusing on data integrity and security.",
                            "Used Retrofit and Gson for API integration and JSON data handling.",
                            "Assisted in updating applications to comply with newer Android SDK releases and design guidelines."
                        )
                    ),
                    Experience(
                        role = "Lead Mobile App/Game Developer",
                        company = "8appstudio",
                        period = "June 2012 – November 2013",
                        highlights = listOf(
                            "Led mobile app and game development projects using Android SDK and Corona SDK frameworks.",
                            "Designed and developed engaging mobile games with smooth performance and intuitive gameplay.",
                            "Directed a small development team, defining workflows, coding standards, and version control practices."
                        )
                    )
                ),
                education = "Bachelor of Science in Computer Science - Notre Dame University of Cotabato 2012",
                languages = listOf("English: Fluent", "Filipino: Fluent")
            )
        )
    }
}
