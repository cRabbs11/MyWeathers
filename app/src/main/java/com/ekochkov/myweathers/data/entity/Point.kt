package com.ekochkov.myweathers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ekochkov.myweathers.data.AppDatabase

@Entity(tableName = AppDatabase.POINTS_TABLE_NAME)
data class Point (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "name") val name: String,
    @Ignore var weather: Weather? = null,
    @ColumnInfo(name = "createdByUser") val createdByUser: Boolean? = false
) {
    constructor(id: Int, latitude: Double, longitude: Double, name: String, createdByUser: Boolean) : this(
        id = id,
        latitude = latitude,
        longitude = longitude,
        name = name,
        weather = null,
        createdByUser = createdByUser
    )
}