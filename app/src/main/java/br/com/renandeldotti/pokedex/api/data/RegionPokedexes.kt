package br.com.renandeldotti.pokedex.api.data

data class RegionPokedexes(
    val id: Int,
    val pokedexes: List<Pokedexes>
)

data class Pokedexes(
    val name: String,
    val url: String
)