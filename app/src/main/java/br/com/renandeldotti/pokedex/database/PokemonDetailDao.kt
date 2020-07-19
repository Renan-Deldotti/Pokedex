package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDetailDao {
    @Insert
    suspend fun insert(vararg pokemonDetail: PokemonDetail): List<Long>

    @Query ("SELECT * FROM pokemon_detail_table WHERE pokemonId = :pokeId")
    fun getPokemonById(pokeId: Int): LiveData<PokemonDetail>

    @Query ("DELETE FROM pokemon_detail_table")
    suspend fun deleteAllPokemonDetailData(): Int
}