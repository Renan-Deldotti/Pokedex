package br.com.renandeldotti.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokedexes_table")
data class Pokedexes(
    val pokedexes: List<Pokedex>,
    val fromRegionId: Int
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}

data class Pokedex(
    val name: String,
    val pokedexId: Int
)