package com.mevalera.holaflytest.domain.usecases

import com.mevalera.holaflytest.data.models.HeroComicsResult

interface GetHeroComicsUseCase {
    suspend operator fun invoke(characterId: Int): List<HeroComicsResult>
}