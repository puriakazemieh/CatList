package com.example.cat.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cat.data.db.entity.CatEntity

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCAt(item: CatEntity)

    @Update
    suspend fun update(item: CatEntity)

    @Query("SELECT * FROM cat where isFav=1 ")
    suspend fun getAllFavCat(): List<CatEntity>?

    @Query("SELECT * FROM cat where id=:id ")
    suspend fun getFavCat(id: String?): CatEntity?

}