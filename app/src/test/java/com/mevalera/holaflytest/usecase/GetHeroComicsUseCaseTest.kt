package com.mevalera.holaflytest.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mevalera.holaflytest.common.TestCoroutineRule
import com.mevalera.holaflytest.data.models.CharacterData
import com.mevalera.holaflytest.data.models.CharactersResponse
import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.data.models.Thumbnail
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepository
import com.mevalera.holaflytest.domain.usecases.GetHeroComicsUseCaseImpl
import com.mevalera.holaflytest.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.Date


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GetHeroComicsUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val heroComicsRepository = Mockito.mock(HeroComicsRepository::class.java)
    private val getHeroComicsUseCase = GetHeroComicsUseCaseImpl(heroComicsRepository)

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

        val mockResult = Result.Success(charactersResponse)
        Mockito.`when`(heroComicsRepository.getHeroComics(1))
            .thenReturn(mockResult)

        // when
        val result = getHeroComicsUseCase(1)

        // then
        assertEquals(2, result.size)
    }

    @Test
    fun returnEmptyListWhenError() = testCoroutineRule.runTest {
        // given
        val result = Result.Error(Exception("Failed to fetch hero comics"))
        Mockito.`when`(heroComicsRepository.getHeroComics(1)).thenReturn(result)

        // when
        val heroComics = getHeroComicsUseCase(1)

        // then
        assertEquals(0, heroComics.size)
    }
}
