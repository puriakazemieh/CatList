package com.example.cat.data

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cat.data.local.db.CatDatabase
import com.example.cat.data.local.db.dao.CatDao
import com.example.cat.data.local.db.entity.CatEntity
import com.example.cat.data.remote.ApiService
import com.example.cat.data.remote.CatPagingSource
import com.example.cat.data.remote.dto.CatDto
import com.example.cat.domain.mapper.toBreedsItem
import com.example.cat.domain.model.Cat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CatRepositoryImplTest {

    private lateinit var repository: CatRepositoryImp
    private val mockApiService = mockk<ApiService>()
    private val mockDatabase = mockk<CatDatabase>()
    private val mockCatDao = mockk<CatDao>()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { mockDatabase.catDao() } returns mockCatDao
        repository = CatRepositoryImp(mockApiService, mockDatabase)
    }


    @SuppressLint("CheckResult")
    @Test
    fun `getCats should return paged data mapped to Cat`() = runTest {
        // Arrange
        val mockApiResponse = listOf(
            CatDto(id = "1", width = 200, height = 200, url = "url1"),
            CatDto(id = "2", width = 300, height = 300, url = "url2"),
            CatDto(id = "3", width = 300, height = 300, url = "url2"),
        )
        coEvery { mockApiService.getCats(page = 0, limit = 10) } returns mockApiResponse

        // Act
        coEvery { mockCatDao.getFavCat("1") } returns CatEntity(id = "1", isFav = true)
        coEvery { mockCatDao.getFavCat("2") } returns null
        coEvery { mockCatDao.getFavCat("3") } returns CatEntity(id = "3", isFav = false)

        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CatPagingSource(mockApiService) }
        )

        val flow = pager.flow.map { pagingData: PagingData<CatDto> ->
            pagingData.map { catDto ->
                val isCatFav = mockCatDao.getFavCat(catDto.id)?.isFav
                catDto.toCat(isCatFav == true)
            }
        }
        val pagingData = flow.first()
        val catList = mutableListOf<Cat>()

        pagingData.map { cat -> catList.add(cat) }

        // Assert
        catList.forEachIndexed { index, cat ->
            when (index) {
                0 -> {
                    assertEquals("1", cat.id)
                    assertTrue(cat.isCatFav)
                }

                1 -> {
                    assertEquals("2", cat.id)
                    assertFalse(cat.isCatFav)
                }

                3 -> {
                    assertEquals("3", cat.id)
                    assertFalse(cat.isCatFav)
                }
            }
        }

    }

    @Test
    fun `getCats should fail when API call throws an exception`() = runTest {
        // Arrange
        val exception = Exception("API call failed")
        coEvery { mockApiService.getCats(page = 0, limit = 10) } throws exception
        coEvery { mockDatabase.catDao() } returns mockCatDao

        // Act & Assert
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CatPagingSource(mockApiService) }
        )

        val flow = pager.flow.map { pagingData: PagingData<CatDto> ->
            pagingData.map { catDto ->
                val isCatFav = mockCatDao.getFavCat(catDto.id)?.isFav
                catDto.toCat(isCatFav == true)
            }
        }

        // Ensure the flow throws an exception
        assertThrows(Exception::class.java) {
            runTest {
                flow.first()
            }
        }
    }

    @Test
    fun `getCats should fail when database call throws an exception`() = runTest {
        // Arrange
        val mockApiResponse = listOf(
            CatDto(id = "1", width = 200, height = 200, url = "url1"),
            CatDto(id = "2", width = 300, height = 300, url = "url2")
        )
        val exception = Exception("Database call failed")
        coEvery { mockApiService.getCats(page = 0, limit = 10) } returns mockApiResponse
        coEvery { mockDatabase.catDao() } returns mockCatDao
        coEvery { mockCatDao.getFavCat(any()) } throws exception

        // Act & Assert
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CatPagingSource(mockApiService) }
        )

        val flow = pager.flow.map { pagingData: PagingData<CatDto> ->
            pagingData.map { catDto ->
                val isCatFav = mockCatDao.getFavCat(catDto.id)?.isFav
                catDto.toCat(isCatFav == true)
            }
        }

        // Ensure the flow throws an exception
        assertThrows(Exception::class.java) {
            runTest {
                flow.first()
            }
        }
    }

    @Test
    fun `getCatDetail should return Flow of Cat when API and database calls are successful`() = runTest {
        // Arrange
        val catDto = CatDto(id = "1", url = "url1", width = 200, height = 200)
        val catEntity = CatEntity(id = "1", isFav = true)
        val expectedCat = Cat(id = "1", imageUrl = "url1", width = 200, height = 200, isCatFav = true)

        coEvery { mockApiService.getCatDetail("1") } returns catDto
        coEvery { mockCatDao.getFavCat("1") } returns catEntity

        // Act
        val result = repository.getCatDetail("1").toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(expectedCat, result[0])
    }

    @Test
    fun `getCatDetail should throw exception when API call fails`() = runTest {
        // Arrange
        val exception = Exception("API call failed")
        coEvery { mockApiService.getCatDetail("1") } throws exception

        // Act & Assert
        assertThrows(Exception::class.java) {
            runTest {
                repository.getCatDetail("1").toList()
            }
        }
    }

    @Test
    fun `getCatDetail should handle null isFav from database`() = runTest {
        // Arrange
        val catDto = CatDto(id = "1", url = "url1", width = 200, height = 200)
        coEvery { mockApiService.getCatDetail("1") } returns catDto
        coEvery { mockCatDao.getFavCat("1") } returns null

        // Act
        val result = repository.getCatDetail("1").toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(false, result[0].isCatFav)
    }

}

fun CatDto.toCat(isCatFav: Boolean) =
    Cat(
        width = width,
        id = id,
        imageUrl = url,
        breeds = breeds?.map { it.toBreedsItem() },
        height = height,
        isCatFav = isCatFav
    )
