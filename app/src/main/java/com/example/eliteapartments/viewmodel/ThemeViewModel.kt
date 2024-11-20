package com.example.eliteapartments.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("theme_preferences")

class ThemeViewModel(context: Context) : ViewModel() {
    private val dataStore = context.dataStore

    private val darkThemeKey = booleanPreferencesKey("dark_theme")

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            dataStore.data.map { preferences ->
                preferences[darkThemeKey] ?: false
            }.collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newThemeState = !_isDarkTheme.value
            _isDarkTheme.value = newThemeState

            dataStore.edit { preferences ->
                preferences[darkThemeKey] = newThemeState
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
                return ThemeViewModel(context) as T
            }
            throw IllegalArgumentException("Clase ViewModel desconocida")
        }
    }
}
