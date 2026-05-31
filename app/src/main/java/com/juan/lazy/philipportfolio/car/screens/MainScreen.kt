package com.juan.lazy.philipportfolio.car.screens

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import com.juan.lazy.philipportfolio.R

class MainScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val profileAction = Action.Builder()
            .setTitle("Profile")
            .setOnClickListener { screenManager.push(ProfileScreen(carContext)) }
            .build()

        val listBuilder = ItemList.Builder()
            .addItem(
                Row.Builder()
                    .setTitle("Technical Skills")
                    .setOnClickListener { screenManager.push(SkillsScreen(carContext)) }
                    .build()
            )
            .addItem(
                Row.Builder()
                    .setTitle("Featured Projects")
                    .setOnClickListener { screenManager.push(ProjectsScreen(carContext)) }
                    .build()
            )
            .addItem(
                Row.Builder()
                    .setTitle("Professional Experience")
                    .setOnClickListener { screenManager.push(ExperienceScreen(carContext)) }
                    .build()
            )

        return ListTemplate.Builder()
            .setSingleList(listBuilder.build())
            .setTitle("Portfolio")
            .setHeaderAction(Action.APP_ICON)
            .addAction(profileAction)
            .build()
    }
}
