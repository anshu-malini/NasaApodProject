package com.am.gsproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.am.gsproject.data.db.daos.ApodDao
import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.Converters

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
                    "am_gs_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return mInstance as AppDatabase
        }
    }
}