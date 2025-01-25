package com.example.cat.data.di

import androidx.room.Room
import com.example.cat.core.constant.BASE_URL
import com.example.cat.data.db.CatDatabase
import com.example.cat.data.db.dao.CatDao
import com.example.cat.data.remote.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CatDatabase::class.java,
            "cat.db"
        ).allowMainThreadQueries().build()
    }
    single<CatDao> {
        val database = get<CatDatabase>()
        database.catDao()
    }
    single { RequestInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }

}


fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


fun provideApiService(retrofit: Retrofit): com.example.cat.data.remote.ApiService =
    retrofit.create(com.example.cat.data.remote.ApiService::class.java)


fun provideOkHttpClient(
    requestInterceptor: RequestInterceptor,
) = OkHttpClient.Builder()
    .apply {
        addNetworkInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
    }
    .addInterceptor(requestInterceptor)
    .build()

