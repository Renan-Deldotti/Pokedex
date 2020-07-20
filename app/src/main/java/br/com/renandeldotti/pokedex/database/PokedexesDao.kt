package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import br.com.renandeldotti.pokedex.database.Pokedexes as p
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokedexesDao {
    @Insert
    suspend fun insert(vararg pokedexes: p): List<Long>

    @Query("DELETE FROM pokedexes_table")
    suspend fun deleteAllPokedexes(): Int

    @Query("SELECT * FROM pokedexes_table")
    fun getAllPokedexes(): LiveData<List<p>>

    /*@Query("SELECT * FROM pokedexes_table WHERE fromRegionId = :region")
    fun getAllPokedexesFromId(region: Int): LiveData<List<p>>*/

    @Query("SELECT * FROM pokedexes_table WHERE pokedexId IN (:ids)")
    fun getPokedexesFromRegion(ids: List<Int>): LiveData<List<p>>

    @Query("SELECT COUNT(*) FROM pokedexes_table WHERE pokedexId IN (:ids)")
    suspend fun countPokedexesFromRegion(ids: List<Int>): Int

    @Query("SELECT COUNT(*) FROM pokedexes_table")
    suspend fun getPokedexesCount(): Int

    /*@Query("SELECT COUNT(*) FROM pokedexes_table WHERE fromRegionId = :region")
    suspend fun getPokedexesFromRegionCount(region: Int): Int*/
}