package com.mevalera.holaflytest.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mevalera.holaflytest.common.TestCoroutineRule
import com.mevalera.holaflytest.data.models.ComicData
import com.mevalera.holaflytest.data.models.ComicResponse
import com.mevalera.holaflytest.data.models.ComicResult
import com.mevalera.holaflytest.data.models.Thumbnail
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepository
import com.mevalera.holaflytest.domain.usecases.GetComicUseCaseImpl
import com.mevalera.holaflytest.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GetComicUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val heroComicsRepository = Mockito.mock(HeroComicsRepository::class.java)
    private val getComicUseCase = GetComicUseCaseImpl(heroComicsRepository)

    @Test
    fun returnHeroComics() = testCoroutineRule.runTest {
        // given
        val expected = ComicResult(
            title = "title 1",
            description = "description 1",
            id = 1,
            thumbnail = Thumbnail("", "")
        )

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

        val charactersResponse = ComicResponse(
            code = 1,
            status = "",
            data = ComicData(
                results = comicResult
            )
        )

        val mockResult = Result.Success(charactersResponse)
        Mockito.`when`(heroComicsRepository.getComic(1))
            .thenReturn(mockResult)

        // when
        val result = getComicUseCase(1)

        // then
        Assert.assertEquals(expected, result)
    }

    @Test
    fun returnEmptyListWhenError() = testCoroutineRule.runTest {
        // given
        val result = Result.Error(Exception("Failed to fetch comic"))
        Mockito.`when`(heroComicsRepository.getComic(1)).thenReturn(result)

        // when
        val comic = getComicUseCase(1)

        // then
        Assert.assertEquals(null, comic)
    }
}
