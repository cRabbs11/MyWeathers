package com.ekochkov.myweathers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ekochkov.myweathers.data.dao.PointDao
import com.ekochkov.myweathers.data.entity.Point

@Database(entities = [Point::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val MAIN_DB_NAME = "weathers_db"
        const val POINTS_TABLE_NAME = "points_table"
    }

    abstract fun pointDao(): PointDao
}