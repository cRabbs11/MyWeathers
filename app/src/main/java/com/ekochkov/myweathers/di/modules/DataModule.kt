package com.ekochkov.myweathers.di.modules

import android.content.Context
import androidx.room.Room
import com.ekochkov.myweathers.data.AppDatabase
import com.ekochkov.myweathers.data.dao.PointDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideUserDao(context: Context): PointDao {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.MAIN_DB_NAME)
            .build()
            .pointDao()
    }
}