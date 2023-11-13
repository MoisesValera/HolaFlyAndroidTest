package com.mevalera.holaflytest.data.models

data class HeroComicsResult(
    val id: Int,
    val title: String? = "",
    val description: String? = "No description available",
    val thumbnail: Thumbnail,
    val modified: String
)

data class ComicResult(
    val id: Int,
    val title: String? = "",
    val description: String? = "No description available",
    val thumbnail: Thumbnail,
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class CharacterData(
    val results: List<HeroComicsResult>
)

data class ComicData(
    val results: List<ComicResult>,
)

data class CharactersResponse(
    val code: Int,
    val status: String,
    val data: CharacterData,
)

data class ComicResponse(
    val code: Int,
    val status: String,
    val data: ComicData,
)
