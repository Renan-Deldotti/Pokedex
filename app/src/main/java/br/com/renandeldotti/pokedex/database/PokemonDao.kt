package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {
    @Insert
    suspend fun insert(vararg pokemon: Pokemon): List<Long>

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAllPokemon(): Int

    @Query ("SELECT * FROM pokemon_table")
    fun getAllPokemon(): LiveData<List<Pokemon>>

    @Query ("SELECT * FROM pokemon_table WHERE fromPokedex = :pokedexId")
    fun getPokemonFromRegion(pokedexId: Int): LiveData<List<Pokemon>>

    @Query ("SELECT COUNT(*) FROM pokemon_table")
    suspend fun getPokemonCount(): Int
}