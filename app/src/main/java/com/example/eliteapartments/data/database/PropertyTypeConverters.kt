package com.example.eliteapartments.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PropertyTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromLocation(value: String): Pair<Double, Double> {
        val type = object : TypeToken<Pair<Double, Double>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun toLocation(pair: Pair<Double, Double>): String {
        return gson.toJson(pair)
    }
}
