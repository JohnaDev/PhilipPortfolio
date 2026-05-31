package com.juan.lazy.philipportfolio.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.juan.lazy.philipportfolio.data.local.PortfolioDatabase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val portfolioRepository: PortfolioRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://raw.githubusercontent.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    private val retrofitService: PortfolioApiService by lazy {
        retrofit.create(PortfolioApiService::class.java)
    }

    override val portfolioRepository: PortfolioRepository by lazy {
        val database = PortfolioDatabase.getDatabase(context)
        NetworkPortfolioRepository(
            context = context,
            portfolioDao = database.portfolioDao(),
            apiService = retrofitService,
            json = json
        )
    }
}
