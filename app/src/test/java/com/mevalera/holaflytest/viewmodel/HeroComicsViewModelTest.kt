package com.mevalera.holaflytest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mevalera.holaflytest.common.TestCoroutineRule
import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.data.models.Thumbnail
import com.mevalera.holaflytest.domain.usecases.GetComicUseCase
import com.mevalera.holaflytest.domain.usecases.GetHeroComicsUseCase
import com.mevalera.holaflytest.presentation.herocomics.HeroComicsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.Date

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HeroComicsViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getComicUseCase = Mockito.mock(GetComicUseCase::class.java)
    private val getHeroComicsUseCase = Mockito.mock(GetHeroComicsUseCase::class.java)
    private val heroComicsViewModel = HeroComicsViewModel(getHeroComicsUseCase, getComicUseCase)

    @Test
    fun getHeroComicsSuccess() = runTest {
        //given
        val heroComics = listOf(
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
            ),
        )
        Mockito.`when`(getHeroComicsUseCase(1)).thenReturn(heroComics)

        //when
        runCurrent()
        heroComicsViewModel.getHeroComics(1)

        //then
        val actual = heroComicsViewModel.selectedHeroComics.value
        assertEquals(heroComics, actual)
    }

    @Test
    fun getHeroComicsEmpty() = testCoroutineRule.runTest {
        //given
        val expected = emptyList<HeroComicsResult>()
        Mockito.`when`(getHeroComicsUseCase(1)).thenReturn(expected)

        //when
        heroComicsViewModel.getHeroComics(1)

        //then
        runCurrent()
        val actual = heroComicsViewModel.selectedHeroComics.value
        assertEquals(expected, actual)
    }
}