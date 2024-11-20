package com.example.eliteapartments.data.database

import androidx.room.*
import com.example.eliteapartments.data.model.Property

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property)

    @Query("SELECT * FROM properties")
    suspend fun getAllProperties(): List<Property>
}
