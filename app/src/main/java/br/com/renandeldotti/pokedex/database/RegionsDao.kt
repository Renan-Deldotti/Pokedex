package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RegionsDao {
    @Insert
    suspend fun insert(vararg regions: Regions): List<Long>

    @Query ("DELETE FROM regions_table")
    suspend fun deleteAllRegions(): Int

    @Query ("SELECT * FROM regions_table")
    fun getAllRegions(): LiveData<List<Regions>>

    @Query ("SELECT COUNT(*) FROM regions_table")
    suspend fun getRegionsCount(): Int
}