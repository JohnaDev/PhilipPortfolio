package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.PortfolioUiState
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    fun getPortfolioData(): Flow<PortfolioUiState>
}
