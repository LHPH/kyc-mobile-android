package com.kyc.mobile.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kyc.mobile.data.local.dao.PropertyDao
import com.kyc.mobile.data.local.entity.PropertyEntity
import com.kyc.mobile.data.local.room.converters.LocalDateTimeStringConverter
import com.kyc.mobile.data.util.SingletonHolder

@Database(
    entities = [ PropertyEntity::class],
    version = 1
)
@TypeConverters(LocalDateTimeStringConverter::class)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun propertyDao(): PropertyDao

    companion object: SingletonHolder<LocalDatabase, Context>( { context ->
        Room.databaseBuilder(
            context = context,
            klass = LocalDatabase::class.java,
            name = "kyc_mobile_database"
        )
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration()
            .build()
    })
}