package com.mevalera.holaflytest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mevalera.holaflytest.common.TestCoroutineRule
import com.mevalera.holaflytest.data.models.CharacterData
import com.mevalera.holaflytest.data.models.CharactersResponse
import com.mevalera.holaflytest.data.models.ComicData
import com.mevalera.holaflytest.data.models.ComicResponse
import com.mevalera.holaflytest.data.models.ComicResult
import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.data.models.Thumbnail
import com.mevalera.holaflytest.data.source.remote.MarvelAPIService
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepositoryImpl
import com.mevalera.holaflytest.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.Date

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HeroComicsRepositoryTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val apiService = Mockito.mock(MarvelAPIService::class.java)
    private val heroComicsRepository = HeroComicsRepositoryImpl(apiService)

    @Test
    fun returnHeroComics() = testCoroutineRule.runTest {
        // given
        val comicsResult = listOf(
            HeroComicsResult(
                title = "title 1",
                description = "description 1",
                id = 1,
                thumbnail = Thumbnail("", ""),
                modified = Date().toString()
            ),
            HeroComicsResult(
                title = "title 2",
                description = "description 2",
                id = 2,
                thumbnail = Thumbnail("", ""),
                modified = Date().toString()
            )
        )

        val charactersResponse = CharactersResponse(
            code = 1,
            status = "",
            data = CharacterData(
                results = comicsResult
            )
        )

        Mockito.`when`(apiService.getHeroComics(1))
            .thenReturn(charactersResponse)

        // when
        val result = heroComicsRepository.getHeroComics(1)

        // then
        Assert.assertEquals(Result.Success(charactersResponse), result)
    }
    @Test
    fun returnComic() = testCoroutineRule.runTest {
        // given
        val comicResult = listOf(
            ComicResult(
                title = "title 1",
                description = "description 1",
                id = 1,
                thumbnail = Thumbnail("", "")
            ),
            ComicResult(
                title = "title 2",
                description = "description 2",
                id = 2,
                thumbnail = Thumbnail("", "")
            )
        )

        val comicResponse = ComicResponse(
            code = 1,
            status = "",
            data = ComicData(
                results = comicResult
            )
        )

        Mockito.`when`(apiService.getComic(1))
            .thenReturn(comicResponse)

        // when
        val result = heroComicsRepository.getComic(1)

        // then
        Assert.assertEquals(Result.Success(comicResponse), result)
    }
    @Test
    fun returnExceptionWhenError() = testCoroutineRule.runTest {
        // given
        val errorMessage = "Failed to fetch hero comics"
        val mockResult = Result.Error(Exception(errorMessage))
        Mockito.`when`(heroComicsRepository.getHeroComics(1)).thenReturn(mockResult)

        // when
        val result = heroComicsRepository.getHeroComics(1)

        // then
        Assert.assertTrue(result is Result.Error)
    }
}
