package br.com.renandeldotti.pokedex.database

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import br.com.renandeldotti.pokedex.Pokedex
import kotlinx.coroutines.*
import java.util.*

/*
    1º -> Should check the connectivity
                If connected to a network then go to 2º
                If not connected then go to 4º
    2º -> Should check everyday for new regions
                If has new regions then update the region and its pokemon
    3º -> Should check every 5 days for new pokemon
                If there is a new pokemon then update the pokemon database
    4º -> Show info from the database
 */
class PokeRepository(private val application: Application) {
    private var regionDao:RegionDao
    private var repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)
    private var hasInternet:Boolean = false

    companion object{
        private const val TAG:String = "PokeRepository"
        private const val REGIONS_LAST_UPDATED:String = "REGIONS_LAST_UPDATED"
        private const val ONE_DAY_IN_MILLI:Long = 86400000L
        private const val FIVE_DAYS_IN_MILLI:Long = 432000000L
    }

    init {
        val database:PokeDatabase = PokeDatabase.getInstance(application)
        regionDao = database.regionDao

        hasInternet = checkInternet()
    }

    private fun checkInternet():Boolean{
        val connectivityManager:ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return (networkCapabilities.hasTransport(TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(TRANSPORT_WIFI)||
                    networkCapabilities.hasTransport(TRANSPORT_ETHERNET))
        } else {
            connectivityManager.activeNetworkInfo?.let {return it.isConnectedOrConnecting}
        }
        return false
    }

    private fun getRegions(){
        if(hasInternet){
            //
        }
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

        updateLastUpdatedPreference()
    }

    /*private fun checkLastUpdated(){
        val sharedPreferences = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(REGIONS_LAST_UPDATED)){
            val lastUpdated = sharedPreferences.getLong(REGIONS_LAST_UPDATED, 0)
            if ((Date().time - lastUpdated) >= ONE_DAY_IN_MILLI){
                // Should update
                updateLastUpdatedPreference()
            }else if ()
        }else{
            // Do not have any data yet - Should update
            //renewRegionsData()
            updateLastUpdatedPreference()
        }
    }*/

    private fun updateLastUpdatedPreference(){
        val sharedPreferencesEditor = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE).edit()
        sharedPreferencesEditor.putLong(REGIONS_LAST_UPDATED, Date().time)
        sharedPreferencesEditor.apply()
    }

    fun cancelRepositoryJobs() = repositoryJob.cancel()
}