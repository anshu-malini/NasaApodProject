package com.am.project.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.am.project.data.db.entities.ApodEntity

@Dao
interface ApodDao {
    @Query("SELECT * FROM apod WHERE apod_id = :apodId")
    fun getApodsById(apodId: Long): ApodEntity

    @Query("SELECT * FROM apod WHERE date = :date")
    fun getApodsByDate(date: String): List<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApods(apods: List<ApodEntity>): List<Long>

    @Query("Update apod SET isFav = :isFavValue WHERE RowId = :apodId")
    fun setApodIsFav(apodId: Long, isFavValue: String): Int

    @Query("SELECT * FROM apod WHERE isFav = :isFavValue")
    fun getApodsByIsFav(isFavValue: String = "Y"): List<ApodEntity>

    @Query("SELECT * FROM apod")
    fun getAllEntries(): List<ApodEntity>


}