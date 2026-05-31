package com.juan.lazy.philipportfolio.car.screens

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import com.juan.lazy.philipportfolio.PortfolioApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SkillsScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val repository = (carContext.applicationContext as PortfolioApplication).container.portfolioRepository
        val data = runBlocking { repository.getPortfolioData().first() }
        
        val listBuilder = ItemList.Builder()
        
        data.skills.forEach { (category, skills) ->
            listBuilder.addItem(
                Row.Builder()
                    .setTitle(category)
                    .addText(skills.joinToString(", "))
                    .build()
            )
        }

        return ListTemplate.Builder()
            .setSingleList(listBuilder.build())
            .setTitle("Technical Skills")
            .setHeaderAction(Action.BACK)
            .build()
    }
}

class ProjectsScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val repository = (carContext.applicationContext as PortfolioApplication).container.portfolioRepository
        val data = runBlocking { repository.getPortfolioData().first() }

        val listBuilder = ItemList.Builder()
        data.projects.forEach { project ->
            listBuilder.addItem(
                Row.Builder()
                    .setTitle(project.title)
                    .addText("${project.role} • ${project.technologies}")
                    .build()
            )
        }

        return ListTemplate.Builder()
            .setSingleList(listBuilder.build())
            .setTitle("Projects")
            .setHeaderAction(Action.BACK)
            .build()
    }
}

class ExperienceScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val repository = (carContext.applicationContext as PortfolioApplication).container.portfolioRepository
        val data = runBlocking { repository.getPortfolioData().first() }

        val listBuilder = ItemList.Builder()
        data.experiences.forEach { exp ->
            listBuilder.addItem(
                Row.Builder()
                    .setTitle(exp.role)
                    .addText("${exp.company} (${exp.period})")
                    .build()
            )
        }

        return ListTemplate.Builder()
            .setSingleList(listBuilder.build())
            .setTitle("Experience")
            .setHeaderAction(Action.BACK)
            .build()
    }
}
