package br.com.renandeldotti.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RegionDao {
    @Insert
    fun insert(vararg region: Region):List<Long>

    @Query ("DELETE FROM regions_table")
    fun deleteAllRegions()

    @Query("SELECT * FROM regions_table ORDER BY regionName")
    fun getAllRegions():LiveData<List<Region>>

    @Query("SELECT COUNT(*) FROM regions_table")
    suspend fun getQuantityOfRegions():Int
}