package com.kyc.mobile.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.kyc.mobile.data.local.entity.PropertyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: PropertyEntity)

    @Update
    suspend fun updateProperty(property: PropertyEntity)

    @Delete
    suspend fun deleteProperty(property: PropertyEntity)

    @Query(value = "SELECT * FROM PROPERTIES")
    fun getAllProperties(): Flow<PropertyEntity>

    @Query(value = "SELECT * FROM PROPERTIES WHERE id =:id")
    suspend fun getPropertyById(id: Int): PropertyEntity?

    @Query(value = "SELECT * FROM PROPERTIES WHERE property_key =:propertyKey")
    suspend fun getPropertyByPropertyKey(propertyKey: String): PropertyEntity?
}