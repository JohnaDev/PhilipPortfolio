package com.juan.lazy.philipportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.juan.lazy.philipportfolio.data.FakePortfolioRepository
import com.juan.lazy.philipportfolio.ui.PortfolioScreen
import com.juan.lazy.philipportfolio.ui.PortfolioViewModel
import com.juan.lazy.philipportfolio.ui.PortfolioViewModelFactory
import com.juan.lazy.philipportfolio.ui.theme.PhilipPortfolioTheme

class MainActivity : ComponentActivity() {

    // Manual Dependency Injection - In a larger app, use Hilt or Koin
    private val viewModel: PortfolioViewModel by viewModels {
        PortfolioViewModelFactory(FakePortfolioRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhilipPortfolioTheme {
                // Collecting state from ViewModel
                val uiState by viewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PortfolioScreen(
                        uiState = uiState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
