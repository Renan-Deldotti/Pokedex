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

    fun getAllRegions():LiveData<List<Region>>{
        return regionDao.getAllRegions()
    }

    fun insertRegions(vararg region: Region) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val returned = regionDao.insert(*region)
                Log.e("OWENQOIENWQ", "teste = $returned")
            }
        }
    }

    fun deleteRegions(){
        val ioScope = CoroutineScope(Dispatchers.IO + repositoryJob)
        ioScope.launch { regionDao.deleteAllRegions() }
    }

    fun cancelRepositoryJob(){
        repositoryJob.cancel()
    }
}