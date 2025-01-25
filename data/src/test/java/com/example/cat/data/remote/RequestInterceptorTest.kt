package com.example.cat.data.remote

import com.example.cat.data.remote.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class RequestInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var requestInterceptor: RequestInterceptor
    private lateinit var okHttpClient: OkHttpClient

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        requestInterceptor = RequestInterceptor()
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `intercept should add api_key query parameter to request`() {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val originalRequest = Request.Builder()
            .url(mockWebServer.url("/test"))
            .build()

        // Act
        val response = okHttpClient.newCall(originalRequest).execute()

        // Assert
        assertEquals(200, response.code)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            "live_cYUVmXAJNt4lZwhgXRpYYL2k5wS09I0sOAKxyIPHBC1StHioVKeKAeuRLOCIpaV1",
            recordedRequest.requestUrl?.queryParameter("api_key")
        )
    }

}