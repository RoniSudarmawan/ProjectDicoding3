package com.example.project3dicoding.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Favorite)

    @Delete
    fun delete(note: Favorite)

    @Query("SELECT * from Favorite ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Favorite>>
}