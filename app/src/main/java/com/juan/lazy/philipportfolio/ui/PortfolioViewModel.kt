package com.juan.lazy.philipportfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.lazy.philipportfolio.data.PortfolioRepository
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PortfolioViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    private val _selectedTheme = MutableStateFlow(AppTheme.SYSTEM)

    val uiState: StateFlow<PortfolioUiState> = repository.getPortfolioData()
        .combine(_selectedTheme) { state, theme ->
            state.copy(selectedTheme = theme)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PortfolioUiState(isLoading = false) // Start without loading visible
        )

    fun onThemeSelected(theme: AppTheme) {
        _selectedTheme.value = theme
    }
}
