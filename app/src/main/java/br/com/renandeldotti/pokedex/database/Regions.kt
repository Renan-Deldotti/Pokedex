package br.com.renandeldotti.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions_table")
data class Regions(
    val name: String,
    val url: String,
    val pokedexes: List<Pokedex>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

data class Pokedex(
    val name: String,
    val pokedexId: Int
)