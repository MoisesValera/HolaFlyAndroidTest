package com.mevalera.holaflytest.data.source.repositories

import com.mevalera.holaflytest.data.models.CharactersResponse
import com.mevalera.holaflytest.data.models.ComicResponse
import com.mevalera.holaflytest.util.Result

interface HeroComicsRepository {
    suspend fun getHeroComics(characterId: Int): Result<CharactersResponse>
    suspend fun getComic(comicId: Int): Result<ComicResponse>
}