package com.example.eliteapartments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eliteapartments.data.database.AppDatabase

class PropertyViewModelFactory(
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropertyViewModel(database.propertyDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
