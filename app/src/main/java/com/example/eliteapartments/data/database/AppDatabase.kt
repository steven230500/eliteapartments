package com.example.eliteapartments.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eliteapartments.data.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
@TypeConverters(PropertyTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}
