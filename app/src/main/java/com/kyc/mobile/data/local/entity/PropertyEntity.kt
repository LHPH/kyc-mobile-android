package com.kyc.mobile.data.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.kyc.mobile.domain.model.LocalProperty
import java.time.LocalDateTime

@Entity(tableName = "PROPERTIES")
data class PropertyEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "PROPERTY_KEY")
    val propertyKey:  String,
    @ColumnInfo(name = "PROPERTY_VALUE")
    val propertyValue: String?,
    @ColumnInfo(name = "CREATE_AT")
    val createAt: LocalDateTime,
    @ColumnInfo(name = "UPDATE_AT")
    val updateAt: LocalDateTime? = null
)

fun PropertyEntity.toModel() = LocalProperty(id,propertyKey,propertyValue)

fun LocalProperty.toNewEntity() = PropertyEntity(0,propertyName,propertyValue, LocalDateTime.now())