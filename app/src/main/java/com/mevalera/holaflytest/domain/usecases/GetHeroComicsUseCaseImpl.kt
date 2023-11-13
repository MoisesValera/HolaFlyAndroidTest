package com.mevalera.holaflytest.domain.usecases

import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepository
import com.mevalera.holaflytest.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHeroComicsUseCaseImpl @Inject constructor(private val heroComicsRepository: HeroComicsRepository) :
    GetHeroComicsUseCase {

    override suspend operator fun invoke(characterId : Int): List<HeroComicsResult> {
        return when (val heroComicsList = heroComicsRepository.getHeroComics(characterId = characterId)) {
            is Result.Success -> {
                heroComicsList.data.data.results.filter { !it.title.isNullOrEmpty() && !it.description.isNullOrEmpty() }
            }
            else -> {
                emptyList()
            }
        }
    }
}
