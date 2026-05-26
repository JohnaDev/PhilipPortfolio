package com.juan.lazy.philipportfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juan.lazy.philipportfolio.data.PortfolioRepository

class PortfolioViewModelFactory(private val repository: PortfolioRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PortfolioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
