package br.com.renandeldotti.pokedex.api.data

data class PokemonDataFromApi(
    val base_experience : Int,
    val height : Int,
    val name : String,
    val stats : List<Stats>,
    val types : List<Types>,
    val weight : Int
)

data class Stats (
    val base_stat : Int
)

data class Types (
    val type : Type
)

data class Type (
    val name : String,
    val url : String
)