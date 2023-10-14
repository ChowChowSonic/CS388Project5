package com.codepath.articlesearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM days")
    fun getAll(): Flow<List<Day>>

    @Insert
    fun insertAll(articles: List<Day>)

    @Insert(entity = Day::class)
    fun insertOne(day:Day)

    @Query("DELETE FROM days")
    fun deleteAll()
}