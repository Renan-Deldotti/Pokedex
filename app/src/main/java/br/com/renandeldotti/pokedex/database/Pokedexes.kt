package br.com.renandeldotti.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokedexes_table")
data class Pokedexes(
    val name: String,
    val pokedexId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}