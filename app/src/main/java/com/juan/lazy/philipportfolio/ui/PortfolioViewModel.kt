package com.juan.lazy.philipportfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.lazy.philipportfolio.data.PortfolioRepository
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PortfolioViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    val uiState: StateFlow<PortfolioUiState> = repository.getPortfolioData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PortfolioUiState()
        )
}
