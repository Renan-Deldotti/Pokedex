package br.com.renandeldotti.pokedex.api.data

data class PokemonDescriptionFromApi(
    val base_happiness: Int,
    val capture_rate: Int,
    val flavor_text_entries: List<FlavorTextEntries>,
    val habitat: Habitat
)

data class FlavorTextEntries(
    val flavor_text: String
)

data class Habitat(
    val name: String,
    val url: String
)