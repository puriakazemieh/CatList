package com.example.cat.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val urlWithQueryParam = originalRequest
            .url
            .newBuilder()
            .addQueryParameter(
                "api_key",
                "live_cYUVmXAJNt4lZwhgXRpYYL2k5wS09I0sOAKxyIPHBC1StHioVKeKAeuRLOCIpaV1"
            )
            .build()

        val requestBuilder = originalRequest
            .newBuilder()
            .url(urlWithQueryParam)

        return chain.proceed(requestBuilder.build())
    }
}
