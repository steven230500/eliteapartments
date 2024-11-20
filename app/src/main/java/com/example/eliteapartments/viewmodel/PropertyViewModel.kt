package com.example.eliteapartments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eliteapartments.data.database.PropertyDao
import com.example.eliteapartments.data.model.Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PropertyViewModel(private val propertyDao: PropertyDao) : ViewModel() {

    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)
    val location: StateFlow<Pair<Double, Double>?> = _location

    val type = MutableStateFlow("")
    val maxGuests = MutableStateFlow("")
    val beds = MutableStateFlow("")
    val bathrooms = MutableStateFlow("")
    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val photos = MutableStateFlow<List<String>>(emptyList())

    private val _properties = MutableStateFlow<List<Property>>(emptyList())
    val properties: StateFlow<List<Property>> = _properties

    fun setLocation(location: Pair<Double, Double>) {
        _location.value = location
    }

    fun setType(value: String) {
        type.value = value
    }

    fun setMaxGuests(value: String) {
        maxGuests.value = value
    }

    fun setBeds(value: String) {
        beds.value = value
    }

    fun setBathrooms(value: String) {
        bathrooms.value = value
    }

    fun setTitle(value: String) {
        title.value = value
    }

    fun setDescription(value: String) {
        description.value = value
    }

    fun setPhotos(value: List<String>) {
        photos.value = value
    }

    fun addProperty(property: Property) {
        viewModelScope.launch {
            propertyDao.insertProperty(property)
            _properties.value = propertyDao.getAllProperties()
        }
    }

    fun loadProperties() {
        viewModelScope.launch {
            _properties.value = propertyDao.getAllProperties()
        }
    }

    fun resetForm() {
        type.value = ""
        maxGuests.value = ""
        beds.value = ""
        bathrooms.value = ""
        title.value = ""
        description.value = ""
        photos.value = emptyList()
        _location.value = null
    }

}
