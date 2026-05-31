package com.juan.lazy.philipportfolio.data

import com.juan.lazy.philipportfolio.model.PortfolioData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PortfolioApiService(private val client: HttpClient) {
    suspend fun getPortfolio(): PortfolioData {
        return client.get("https://raw.githubusercontent.com/JohnaDev/portfolio/main/portfolio.json").body()
    }
}
