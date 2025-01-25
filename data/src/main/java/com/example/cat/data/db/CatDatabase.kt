package com.example.cat.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cat.data.db.dao.CatDao
import com.example.cat.data.db.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}