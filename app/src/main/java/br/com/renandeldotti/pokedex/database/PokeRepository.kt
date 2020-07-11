package br.com.renandeldotti.pokedex.database

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import br.com.renandeldotti.pokedex.Pokedex
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.data.Results
import br.com.renandeldotti.pokedex.ui.region.RegionViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

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
    private val pokeApi: RetrofitPokeApi = RetrofitPokeApi()
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
        checkLastUpdated()
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

    /**
     * Should update only if has internet and the last update was before
     * 5 days or (1 day + changes)
     */
    private fun checkLastUpdated(){
        if (!hasInternet){
            return
        }
        val sharedPreferences = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(REGIONS_LAST_UPDATED)){
            val lastUpdated = sharedPreferences.getLong(REGIONS_LAST_UPDATED, 0)
            if ((Date().time - lastUpdated) >= FIVE_DAYS_IN_MILLI){
                fetchRegionsData()
                //fetchPokemonData()
            }else if ((Date().time - lastUpdated) >= ONE_DAY_IN_MILLI){
                uiScope.launch {
                    var qnt = 0
                    withContext(Dispatchers.IO){
                        qnt = regionDao.getQuantityOfRegions()
                    }
                    fetchRegionsData(qnt)
                }
            }
        }else{
            // Do not have any data yet - Should update
            fetchRegionsData()
        }
    }

    private fun fetchRegionsData(storedSize:Int = -1){
        val regionCall: Call<br.com.renandeldotti.pokedex.data.Region> = pokeApi.getPokeApi().getRegions()
        regionCall.enqueue(object : Callback<br.com.renandeldotti.pokedex.data.Region> {
            override fun onFailure(call: Call<br.com.renandeldotti.pokedex.data.Region>, t: Throwable) {
                Log.e(RegionViewModel.TAG,"Error: "+t.message)
            }

            override fun onResponse(call: Call<br.com.renandeldotti.pokedex.data.Region>, response: Response<br.com.renandeldotti.pokedex.data.Region>) {
                response.body()?.run {
                    if (storedSize == -1) {
                        renewRegionsData(this.results)
                    }else{
                        //Log.e(TAG, "storedSize = $storedSize this.results.size = ${this.results.size}")
                        if(storedSize != this.results.size){
                            renewRegionsData(this.results)
                        }
                    }
                }
            }
        })
    }

    fun renewRegionsData(list:List<Results>){
        uiScope.launch {
            withContext(Dispatchers.IO){
                regionDao.deleteAllRegions()
                val formattedList = ArrayList<Region>()
                list.forEach{
                    formattedList.add(Region(it.name, it.url))
                }
                val idsReturned = regionDao.insert(*formattedList.toTypedArray())
                Log.e(TAG, "IDS_RETURNED -> $idsReturned")
            }
        }
        updateLastUpdatedPreference()
    }

    private fun updateLastUpdatedPreference(){
        val sharedPreferencesEditor = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE).edit()
        sharedPreferencesEditor.putLong(REGIONS_LAST_UPDATED, Date().time)
        sharedPreferencesEditor.apply()
    }

    fun getAllRegions():LiveData<List<Region>> = regionDao.getAllRegions()

    fun cancelRepositoryJobs() = repositoryJob.cancel()
}