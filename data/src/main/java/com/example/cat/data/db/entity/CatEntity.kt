package com.example.cat.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val isFav: Boolean = false,
)
