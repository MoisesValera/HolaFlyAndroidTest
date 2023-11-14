package com.mevalera.holaflytest.data.source.remote

import com.mevalera.holaflytest.data.models.CharactersResponse
import com.mevalera.holaflytest.data.models.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MarvelAPIService {
    @GET("characters/{characterId}/comics")
    suspend fun getHeroComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String = "1000",
        @Query("apikey") apiKey: String = API_KEY,
        @Query("hash") hash: String = HASH,
    ): CharactersResponse

    @GET("comics/{comicId}")
    suspend fun getComic(
        @Path("comicId") comicId: Int,
        @Query("ts") ts: String = "1000",
        @Query("apikey") apiKey: String = API_KEY,
        @Query("hash") hash: String = HASH
    ): ComicResponse
}

const val API_KEY = "04ebc8f70feb0da64b3b308d0304eeb3"
const val HASH = "916b511fb1dbb071cb15f4ff7d4c61a7"