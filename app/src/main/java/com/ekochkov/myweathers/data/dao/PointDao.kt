package com.ekochkov.myweathers.data.dao

import androidx.room.*
import com.ekochkov.myweathers.data.AppDatabase
import com.ekochkov.myweathers.data.entity.Point
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Query("SELECT * FROM ${AppDatabase.POINTS_TABLE_NAME} WHERE id LIKE:id")
    fun getPoint(id: Int) : Point?

    @Query("SELECT * FROM ${AppDatabase.POINTS_TABLE_NAME}")
    fun getPointsFlow() : Flow<List<Point>>

    @Update
    fun updateUser(point: Point)

    @Delete
    fun deletePoint(point: Point) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoint(point: Point): Long
}