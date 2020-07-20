package br.com.renandeldotti.pokedex.api.data

data class PokedexesFromApi(
    val results: List<PokedexesResults>
)

data class PokedexesResults(
    val name: String,
    val url: String
)