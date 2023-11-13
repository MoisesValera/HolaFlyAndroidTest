package com.mevalera.holaflytest.data.source.repositories

import com.mevalera.holaflytest.data.models.CharactersResponse
import com.mevalera.holaflytest.data.models.ComicResponse
import com.mevalera.holaflytest.data.source.remote.MarvelAPIService
import com.mevalera.holaflytest.util.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HeroComicsRepositoryImpl @Inject constructor(
    private val apiService: MarvelAPIService
) : HeroComicsRepository {

    override suspend fun getHeroComics(characterId: Int): Result<CharactersResponse> {
        return try {
            val response = apiService.getHeroComics(characterId)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getComic(comicId : Int): Result<ComicResponse> {
        return try {
            val response = apiService.getComic(comicId = comicId)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}