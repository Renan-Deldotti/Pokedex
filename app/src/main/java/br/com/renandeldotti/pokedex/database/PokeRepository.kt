package br.com.renandeldotti.pokedex.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class PokeRepository(application: Application) {
    private var regionDao:RegionDao
    private var repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)

    init {
        val database:PokeDatabase = PokeDatabase.getInstance(application)
        regionDao = database.regionDao
    }

    fun getAllRegions():LiveData<List<Region>> = regionDao.getAllRegions()

    fun renewRegionsData(vararg region: Region){
        uiScope.launch {
            withContext(Dispatchers.IO){
                regionDao.deleteAllRegions()
                val idsReturned = regionDao.insert(*region)
                Log.e("IDS_RETURNED", "$idsReturned")
            }
        }
    }

    fun cancelRepositoryJobs() = repositoryJob.cancel()
}