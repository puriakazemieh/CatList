package com.example.cat.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCats should return list of cats`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setBody(
                """
                [
                    {
                        "id": "1",
                        "width": 200,
                        "height": 200,
                        "url": "url1"
                    },
                    {
                        "id": "2",
                        "width": 300,
                        "height": 300,
                        "url": "url2"
                    }
                ]
            """.trimIndent()
            )
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Act
        val cats = apiService.getCats(page = 0)

        // Assert
        assertEquals(2, cats.size)
        assertEquals("1", cats[0].id)
        assertEquals("url1", cats[0].url)
        assertEquals("2", cats[1].id)
        assertEquals("url2", cats[1].url)
    }

    @Test
    fun `getCatDetail should return cat details`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setBody(
                """
            {
                "id": "1",
                "width": 200,
                "height": 200,
                "url": "url1"
            }
        """.trimIndent()
            )
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Act
        val cat = apiService.getCatDetail("1")

        // Assert
        assertEquals("1", cat.id)
        assertEquals("url1", cat.url)
    }
}