package com.example.eliteapartments.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val maxGuests: Int,
    val beds: Int,
    val bathrooms: Int,
    val title: String,
    val description: String,
    val photos: List<String>,
    val location: Pair<Double, Double>
)
