package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.PortfolioData
import retrofit2.http.GET

interface PortfolioApiService {
    @GET("JohnaDev/portfolio/main/portfolio.json")
    suspend fun getPortfolio(): PortfolioData
}
