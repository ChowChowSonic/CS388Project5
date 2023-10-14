package com.codepath.articlesearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class Day(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "steps") val steps: Int?,
    @ColumnInfo(name = "caloriesEaten") val caloriesEaten: Int?,
    @ColumnInfo(name = "sleepHrs") val sleepHrs: Int?,
    @ColumnInfo(name = "waterGlasses") val waterGlasses: Int?
)