package com.mevalera.holaflytest.di

import android.content.Context
import com.mevalera.holaflytest.BuildConfig
import com.mevalera.holaflytest.data.source.remote.MarvelAPIService
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepository
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepositoryImpl
import com.mevalera.holaflytest.domain.usecases.GetComicUseCase
import com.mevalera.holaflytest.domain.usecases.GetComicUseCaseImpl
import com.mevalera.holaflytest.domain.usecases.GetHeroComicsUseCase
import com.mevalera.holaflytest.domain.usecases.GetHeroComicsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HeroesComicsModule {
    @Singleton
    @Provides
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMarvelApiService(retrofit: Retrofit): MarvelAPIService =
        retrofit.create(MarvelAPIService::class.java)

    @Provides
    @Singleton
    fun provideHeroComicsRepositoryImpl(repository: HeroComicsRepositoryImpl): HeroComicsRepository {
        return repository
    }


    @Provides
    @Singleton
    fun provideGetHeroComicsUseCase(heroComicsRepository: HeroComicsRepository): GetHeroComicsUseCase {
        return GetHeroComicsUseCaseImpl(heroComicsRepository)
    }

    @Provides
    @Singleton
    fun provideGetComicsUseCase(heroComicsRepository: HeroComicsRepository): GetComicUseCase {
        return GetComicUseCaseImpl(heroComicsRepository)
    }
}

object ContextProvider {
    private var applicationContext: Context? = null

    fun setContext(context: Context) {
        applicationContext = context.applicationContext
    }
}