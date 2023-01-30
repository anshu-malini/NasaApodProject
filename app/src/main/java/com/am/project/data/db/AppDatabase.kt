package com.am.project.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.am.project.data.db.daos.ApodDao
import com.am.project.data.db.entities.ApodEntity
import com.am.project.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [ApodEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao
    companion object {
        private var mInstance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "am_nasa_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return mInstance as AppDatabase
        }
    }
}