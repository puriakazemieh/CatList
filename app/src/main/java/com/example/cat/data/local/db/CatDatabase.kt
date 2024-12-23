package com.example.cat.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cat.data.local.db.dao.CatDao
import com.example.cat.data.local.db.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}