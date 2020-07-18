package br.com.renandeldotti.pokedex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class Pokemon(
    val nome: String,
    val url: String,
    val fromPokedex: Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}