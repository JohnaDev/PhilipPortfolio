package com.juan.lazy.philipportfolio.car

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.juan.lazy.philipportfolio.car.screens.MainScreen

class PortfolioSession : Session() {
    override fun onCreateScreen(intent: Intent): Screen {
        return MainScreen(carContext)
    }
}
