package com.example.cat.di

import androidx.room.Room
import com.example.cat.data.CatRepositoryImp
import com.example.cat.data.local.db.CatDatabase
import com.example.cat.data.local.db.dao.CatDao
import com.example.cat.data.remote.ApiService
import com.example.cat.data.remote.interceptor.RequestInterceptor
import com.example.cat.domain.CatRepository
import com.example.cat.domain.usecase.GetCatDetailUseCase
import com.example.cat.domain.usecase.GetCatsUseCase
import com.example.cat.domain.usecase.SetFavUseCase
import com.example.cat.domain.usecase.UseCase
import com.example.cat.ui.presentation.detail.DetailViewmodel
import com.example.cat.ui.presentation.home.HomeViewmodel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
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
    singleOf(::CatRepositoryImp) { bind<CatRepository>() }
    single { provideCases(get()) }
    viewModelOf(::HomeViewmodel)
    viewModelOf(::DetailViewmodel)

}

const val BASE_URL = "https://api.thecatapi.com/v1/"

fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)


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


fun provideCases(
    repository: CatRepository,
): UseCase {
    return UseCase(
        getCatsUseCase = GetCatsUseCase(repository),
        getCatDetailUseCase = GetCatDetailUseCase(repository),
        setFavUseCase = SetFavUseCase(repository),
    )
}
