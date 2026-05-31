package com.juan.lazy.philipportfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.lazy.philipportfolio.data.PortfolioRepository
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.SyncStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    private val _selectedTheme = MutableStateFlow(AppTheme.SYSTEM)
    private val _syncStatusOverride = MutableStateFlow<SyncStatus?>(null)

    val uiState: StateFlow<PortfolioUiState> = repository.getPortfolioData()
        .onEach { state ->
            // When we get a SUCCESS or ERROR status, start a timer to reset it to IDLE
            if (state.syncStatus == SyncStatus.SUCCESS || state.syncStatus == SyncStatus.ERROR) {
                viewModelScope.launch {
                    delay(2000)
                    _syncStatusOverride.value = SyncStatus.IDLE
                }
            } else {
                _syncStatusOverride.value = null
            }
        }
        .combine(_selectedTheme) { state, theme ->
            state.copy(selectedTheme = theme)
        }
        .combine(_syncStatusOverride) { state, overrideStatus ->
            if (overrideStatus != null) state.copy(syncStatus = overrideStatus) else state
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PortfolioUiState(isLoading = true)
        )

    fun onThemeSelected(theme: AppTheme) {
        _selectedTheme.value = theme
    }

    fun onRefreshTriggered() {
        // This is a placeholder. Since repository.getPortfolioData() returns a Cold Flow that fetches data on collection,
        // triggering a "refresh" would normally involve re-collecting or having a trigger flow in the repository.
        // For now, let's just log it.
        android.util.Log.e("ViewModelSync", "onRefreshTriggered called")
    }
}
