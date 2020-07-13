package br.com.renandeldotti.pokedex.api.data

data class PokemonListFromPokedex(
    val pokemon_entries: List<PokemonEntries>
)

data class PokemonEntries(
    val pokemon_species: PokemonSpecies
)

data class PokemonSpecies(
    val name: String,
    val url: String
)