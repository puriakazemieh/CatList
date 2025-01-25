package com.example.cat

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cat.data.local.db.CatDatabase
import com.example.cat.data.local.db.dao.CatDao
import com.example.cat.data.local.db.entity.CatEntity
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatDaoTest {

    private lateinit var database: CatDatabase
    private lateinit var catDao: CatDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatDatabase::class.java
        ).allowMainThreadQueries().build()

        catDao = database.catDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCatTest() = runBlocking {
        // Given
        val cat = CatEntity(id = "1", isFav = true)

        // When
        catDao.insertCAt(cat)

        // Then
        val retrievedCat = catDao.getFavCat("1")
        assertNotNull(retrievedCat)
        assertEquals("1", retrievedCat?.id)
        assertTrue(retrievedCat?.isFav == true)
    }

    @Test
    fun updateCatTest() = runBlocking {
        // Given
        val cat = CatEntity(id = "1", isFav = true)
        catDao.insertCAt(cat)

        // When
        val updatedCat = cat.copy(isFav = false)
        catDao.update(updatedCat)

        // Then
        val retrievedCat = catDao.getFavCat("1")
        assertNotNull(retrievedCat)
        assertEquals("1", retrievedCat?.id)
        assertFalse(retrievedCat?.isFav == true)
    }

    @Test
    fun getAllFavCatTest() = runBlocking {
        // Given
        val cat1 = CatEntity(id = "1", isFav = true)
        val cat2 = CatEntity(id = "2", isFav = false)
        val cat3 = CatEntity(id = "3", isFav = true)
        catDao.insertCAt(cat1)
        catDao.insertCAt(cat2)
        catDao.insertCAt(cat3)

        // When
        val favCats = catDao.getAllFavCat()

        // Then
        assertNotNull(favCats)
        assertEquals(2, favCats?.size)
        assertTrue(favCats?.any { it.id == "1" } == true)
        assertTrue(favCats?.any { it.id == "3" } == true)
    }

    @Test
    fun getFavCatTest() = runBlocking {
        // Given
        val cat = CatEntity(id = "1", isFav = true)
        catDao.insertCAt(cat)

        // When
        val retrievedCat = catDao.getFavCat("1")

        // Then
        assertNotNull(retrievedCat)
        assertEquals("1", retrievedCat?.id)
        assertTrue(retrievedCat?.isFav == true)
    }
}