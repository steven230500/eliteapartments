package com.example.eliteapartments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.eliteapartments.data.database.AppDatabase
import com.example.eliteapartments.ui.navigation.AppNavigation
import com.example.eliteapartments.ui.theme.EliteApartmentsTheme
import com.example.eliteapartments.viewmodel.PropertyViewModel
import com.example.eliteapartments.viewmodel.PropertyViewModelFactory
import com.example.eliteapartments.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val propertyViewModel: PropertyViewModel by viewModels {
        PropertyViewModelFactory(database)
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModel.Factory(applicationContext)
    }

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "elite_apartments.db"
        ).fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()

        setContent {
            val isDarkTheme = themeViewModel.isDarkTheme.collectAsState(initial = false).value

            EliteApartmentsTheme(darkTheme = isDarkTheme) {
                Scaffold { innerPadding ->
                    AppNavigation(
                        propertyViewModel = propertyViewModel,
                        themeViewModel = themeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
