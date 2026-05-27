package com.juan.lazy.philipportfolio.data

import android.content.Context
import com.juan.lazy.philipportfolio.data.local.PortfolioDao
import com.juan.lazy.philipportfolio.data.local.PortfolioEntity
import com.juan.lazy.philipportfolio.model.PortfolioData
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                // Asset reading failed
            }
        }

        var lastKnownData: PortfolioUiState? = null

        if (localData != null) {
            try {
                val portfolio = json.decodeFromString<PortfolioData>(localData.jsonContent)
                lastKnownData = PortfolioMapper.mapToUiState(portfolio)
                emit(lastKnownData.copy(isLoading = false, syncStatus = SyncStatus.SYNCING))
            } catch (e: Exception) {
                // Parsing failed
            }
        }

        // 2. Network Fetch
        try {
            val networkData = apiService.getPortfolio()
            val jsonString = json.encodeToString(networkData)
            portfolioDao.insertPortfolio(PortfolioEntity(jsonContent = jsonString))
            
            val updatedState = PortfolioMapper.mapToUiState(networkData)
            emit(updatedState.copy(isLoading = false, syncStatus = SyncStatus.SUCCESS))
        } catch (e: Exception) {
            if (lastKnownData != null) {
                emit(lastKnownData.copy(isLoading = false, syncStatus = SyncStatus.ERROR))
            } else {
                emit(PortfolioUiState(isLoading = false, syncStatus = SyncStatus.ERROR))
            }
        }
    }
}
