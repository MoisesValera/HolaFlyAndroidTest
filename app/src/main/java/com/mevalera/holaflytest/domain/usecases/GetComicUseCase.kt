package com.mevalera.holaflytest.domain.usecases

import com.mevalera.holaflytest.data.models.ComicResult

interface GetComicUseCase {
    suspend operator fun invoke(comicId: Int): ComicResult?
}