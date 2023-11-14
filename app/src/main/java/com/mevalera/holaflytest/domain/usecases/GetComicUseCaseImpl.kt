package com.mevalera.holaflytest.domain.usecases

import com.mevalera.holaflytest.data.models.ComicResult
import com.mevalera.holaflytest.data.source.repositories.HeroComicsRepository
import com.mevalera.holaflytest.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetComicUseCaseImpl @Inject constructor(private val heroComicsRepository: HeroComicsRepository) :
    GetComicUseCase {
    override suspend operator fun invoke(comicId: Int): ComicResult? {
        return when (val comicResult = heroComicsRepository.getComic(comicId)) {
            is Result.Success -> {
                comicResult.data.data.results.filter { it.title != null && it.description != null }
                    .first()
            }
            else -> null
        }
    }
}