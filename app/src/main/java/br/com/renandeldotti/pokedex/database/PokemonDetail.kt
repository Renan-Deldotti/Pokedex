package br.com.renandeldotti.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail_table")
data class PokemonDetail(
    val base_happiness: Int,
    val capture_rate: Int,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val pokemonId: Int,
    val description: String,
    val habitat_name: String,
    val name: String,
    val stats: List<PokemonStats>,
    val types: List<PokemonTypes>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

data class PokemonStats(
    val base_stat: Int,
    val stat_name: String
)

data class PokemonTypes(
    val slot: Int,
    val type_name: String
)