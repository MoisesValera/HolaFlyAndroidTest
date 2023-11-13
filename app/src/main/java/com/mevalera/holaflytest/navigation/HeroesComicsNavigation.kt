package com.mevalera.holaflytest.navigation

sealed class HeroesComicsNavigation(val route: String) {
    object Welcome : HeroesComicsNavigation(WELCOME)
    object HeroesList : HeroesComicsNavigation(HEROES_LIST)
    object HeroComicsList : HeroesComicsNavigation("$HEROES_COMICS_LIST/{characterId}"){
        fun createRoute(characterId: Int) = "$HEROES_COMICS_LIST/$characterId"
    }
    object ComicScreen : HeroesComicsNavigation("$COMIC/{comicId}"){
        fun createRoute(comicId: Int) = "$COMIC/$comicId"
    }

    companion object{
        const val WELCOME = "welcomeScreen"
        const val HEROES_LIST = "heroesListScreen"
        const val HEROES_COMICS_LIST = "heroesComicsListScreen"
        const val COMIC = "comicScreen"
    }
}