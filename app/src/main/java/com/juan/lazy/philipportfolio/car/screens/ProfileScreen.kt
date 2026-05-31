package com.juan.lazy.philipportfolio.car.screens

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import com.juan.lazy.philipportfolio.PortfolioApplication
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val repository = (carContext.applicationContext as PortfolioApplication).container.portfolioRepository
        
        // Blocking fetch for simplicity in car template
        val data = runBlocking { repository.getPortfolioData().first() }

        val paneBuilder = Pane.Builder()
            .addRow(Row.Builder().setTitle("Name").addText(data.name).build())
            .addRow(Row.Builder().setTitle("Title").addText(data.role).build())
            .addRow(Row.Builder().setTitle("Location").addText(data.location).build())
            .addRow(Row.Builder().setTitle("Email").addText(data.email).build())
            .addRow(Row.Builder().setTitle("Phone").addText(data.phone).build())

        return PaneTemplate.Builder(paneBuilder.build())
            .setTitle("Profile")
            .setHeaderAction(Action.BACK)
            .build()
    }
}
