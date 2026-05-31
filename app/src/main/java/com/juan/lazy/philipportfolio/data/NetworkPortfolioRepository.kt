package com.juan.lazy.philipportfolio.data

import android.content.Context
import android.widget.Toast
import com.juan.lazy.philipportfolio.data.local.PortfolioDao
import com.juan.lazy.philipportfolio.data.local.PortfolioEntity
import com.juan.lazy.philipportfolio.model.PortfolioData
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.SyncStatus
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NetworkPortfolioRepository(
    private val context: Context,
    private val portfolioDao: PortfolioDao,
    private val apiService: PortfolioApiService,
    private val json: Json
) : PortfolioRepository {

    override fun getPortfolioData(): Flow<PortfolioUiState> = flow {
        // 1. Initial Local Data / Assets
        var localData = portfolioDao.getPortfolio()
        
        if (localData == null) {
            try {
                val initialJson = context.assets.open("portfolio_initial.json").bufferedReader().use { it.readText() }
                localData = PortfolioEntity(jsonContent = initialJson)
                portfolioDao.insertPortfolio(localData)
            } catch (e: Exception) {
                android.util.Log.e("SyncError", "Asset loading failed: ${e.message}")
            }
        }

        var lastKnownData: PortfolioUiState? = null

        if (localData != null) {
            try {
                val portfolio = json.decodeFromString<PortfolioData>(localData.jsonContent)
                lastKnownData = PortfolioMapper.mapToUiState(portfolio)
                emit(lastKnownData.copy(isLoading = false, syncStatus = SyncStatus.SYNCING))
            } catch (e: Exception) {
                android.util.Log.e("SyncError", "Local data parsing failed: ${e.message}")
            }
        }

        // 2. Network Fetch
        try {
            android.util.Log.e("SyncTrace", "Starting network fetch...")
            val response = apiService.getPortfolio()
            android.util.Log.e("SyncTrace", "Network fetch completed. Success: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val networkData = response.body()!!
                val jsonString = json.encodeToString(networkData)
                portfolioDao.insertPortfolio(PortfolioEntity(jsonContent = jsonString))
                
                val updatedState = PortfolioMapper.mapToUiState(networkData)
                emit(updatedState.copy(isLoading = false, syncStatus = SyncStatus.SUCCESS))
            } else {
                val errorMsg = "HTTP Error: ${response.code()}"
                android.util.Log.e("SyncError", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = "DEBUG_ERROR: ${e.javaClass.simpleName} - ${e.message}"
            android.util.Log.e("SyncError", errorMsg, e)
            
            // Show a Toast for immediate feedback in release build
            MainScope().launch {
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
            }

            if (lastKnownData != null) {
                emit(lastKnownData.copy(isLoading = false, syncStatus = SyncStatus.ERROR, aboutMe = errorMsg + "\n\n" + lastKnownData.aboutMe))
            } else {
                emit(PortfolioUiState(isLoading = false, syncStatus = SyncStatus.ERROR, aboutMe = errorMsg))
            }
        }
    }
}
