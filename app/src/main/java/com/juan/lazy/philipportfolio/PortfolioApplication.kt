package com.juan.lazy.philipportfolio

import android.app.Application
import com.juan.lazy.philipportfolio.data.AppContainer
import com.juan.lazy.philipportfolio.data.DefaultAppContainer

class PortfolioApplication : Application() {
    /** AppContainer instance used by the rest of the classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
