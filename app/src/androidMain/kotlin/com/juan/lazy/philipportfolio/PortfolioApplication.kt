package com.juan.lazy.philipportfolio

import android.app.Application
import com.juan.lazy.philipportfolio.data.AppContainer
import com.juan.lazy.philipportfolio.data.DefaultAppContainer
import com.juan.lazy.philipportfolio.data.local.getDatabaseBuilder
import com.juan.lazy.philipportfolio.data.local.getRoomDatabase
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import philipportfolio.app.generated.resources.Res

class PortfolioApplication : Application() {
    lateinit var container: AppContainer

    @OptIn(ExperimentalResourceApi::class)
    override fun onCreate() {
        super.onCreate()
        
        val databaseBuilder = getDatabaseBuilder(this)
        val database = getRoomDatabase(databaseBuilder)
        
        val initialJson = runBlocking {
            Res.readBytes("files/portfolio_initial.json").decodeToString()
        }
        
        container = DefaultAppContainer(database, initialJson)
    }
}
