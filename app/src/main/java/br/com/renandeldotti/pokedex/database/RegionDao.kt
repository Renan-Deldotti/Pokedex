package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RegionDao {
    @Insert
    fun insert(vararg region: Region):List<Long>

    @Query ("DELETE FROM region_table")
    fun deleteAllRegions()

    @Query("SELECT * FROM region_table ORDER BY regionName")
    fun getAllRegions():LiveData<List<Region>>

    @Query("SELECT COUNT(*) FROM region_table")
    suspend fun getQuantityOfRegions():Int
}