package com.mevalera.holaflytest.presentation.herocomics

import androidx.lifecycle.ViewModel
import com.mevalera.holaflytest.data.models.ComicResult
import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.domain.usecases.GetComicUseCase
import com.mevalera.holaflytest.domain.usecases.GetHeroComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HeroComicsViewModel @Inject constructor(
    private val getHeroComicsUseCase: GetHeroComicsUseCase,
    private val getComicUseCase: GetComicUseCase,
) :
    ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _selectedHeroComics = MutableStateFlow(mutableListOf<HeroComicsResult>())
    val selectedHeroComics = _selectedHeroComics.asStateFlow()

    private val _comic = MutableStateFlow<ComicResult?>(null)
    val comic = _comic.asStateFlow()

    suspend fun getHeroComics(characterId: Int) {
        _isSearching.update { true }
        _selectedHeroComics.value = getHeroComicsUseCase(characterId = characterId).toMutableList()
        _isSearching.update { false }
    }

    suspend fun getComic(comicId: Int) {
        _isSearching.update { true }
        _comic.value = getComicUseCase(comicId)
        _isSearching.update { false }
    }
}