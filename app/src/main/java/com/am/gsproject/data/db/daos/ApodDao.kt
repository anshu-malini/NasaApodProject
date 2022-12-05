package com.am.gsproject.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.NetworkResult

@Dao
interface ApodDao {
    @Query("SELECT * FROM apod WHERE date = :date")
    fun getApodsByDate(date: String): List<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApods(apods: List<ApodEntity>)

    @Query("Update apod SET isFav = :isFavValue WHERE RowId = :apodId")
    fun setApodIsFav(apodId: Int, isFavValue: Boolean)

    @Query("SELECT * FROM apod WHERE isFav = :isFavValue")
    fun getApodsByIsFav(isFavValue: Boolean = true): List<ApodEntity>


}