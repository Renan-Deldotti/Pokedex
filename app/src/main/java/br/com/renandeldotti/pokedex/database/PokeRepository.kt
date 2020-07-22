package br.com.renandeldotti.pokedex.database

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.Pokedex
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.api.data.PokedexesFromApi
import br.com.renandeldotti.pokedex.api.data.RegionPokedexes
import br.com.renandeldotti.pokedex.api.data.Results
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI
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
    private var regionsDao:RegionsDao
    private var pokemonDao:PokemonDao
    private var pokedexesDao: PokedexesDao
    private var pokemonDetailDao:PokemonDetailDao
    private var repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)
    private var hasInternet:Boolean = false
    private var needUpdate:Boolean = false
    private var pokedexesFromRegionMLV = MutableLiveData<List<Pokedexes>>()

    companion object{
        //private const val TAG:String = "PokeRepository"
        private const val LAST_UPDATE_DATE:String = "LAST_UPDATE_DATE"
        private const val ONE_DAY_IN_MILLI:Long = 86400000L
        private const val TAG:String = "PokeRepository"
    }

    init {
        val database:PokeDatabase = PokeDatabase.getInstance(application)
        regionsDao = database.regionsDao
        pokemonDao = database.pokemonDao
        pokedexesDao = database.pokedexesDao
        pokemonDetailDao = database.pokemonDetailDao

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
     * Used refresh the data
     * obs: method started by the user only
     */
    fun forceRefresh(){
        if (!hasInternet){
            return
        }
        fetchRegionsData()
        Log.e(TAG, "forceRefresh: ")
    }

    /**
     * Should update only if has internet and the last update was yesterday or before
     */
    private fun checkLastUpdated(){
        if (!hasInternet){
            return
        }
        val sharedPreferences = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(LAST_UPDATE_DATE)){
            val lastUpdated = sharedPreferences.getLong(LAST_UPDATE_DATE, 0)
            if ((Date().time - lastUpdated) >= ONE_DAY_IN_MILLI){
                needUpdate = true
                fetchRegionsData()
                fetchPokedexes()
            }
        }else{
            // Do not have any data yet - Should update
            needUpdate = true
            fetchRegionsData()
            fetchPokedexes()
        }
    }

    private fun fetchRegionsData(){
        val regionCall: Call<br.com.renandeldotti.pokedex.api.data.Region> = pokeApi.getPokeApi().getRegions()
        regionCall.enqueue(object : Callback<br.com.renandeldotti.pokedex.api.data.Region> {
            override fun onFailure(call: Call<br.com.renandeldotti.pokedex.api.data.Region>, t: Throwable) {
                Log.e(TAG,"Error: "+t.message)
            }

            override fun onResponse(call: Call<br.com.renandeldotti.pokedex.api.data.Region>, response: Response<br.com.renandeldotti.pokedex.api.data.Region>) {
                response.body()?.run {
                    renewRegionsData(this.results)
                }
            }
        })
    }

    private fun renewRegionsData(list:List<Results>){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val formattedList = ArrayList<Regions>()
                list.forEach{
                    val id: Int = try {
                        URI(it.url).path.substringBeforeLast('/').substringAfterLast('/').toInt()
                    }catch (e: Exception){
                        1
                    }
                    formattedList.add(Regions(it.name, id))
                }
                regionsDao.insert(*formattedList.toTypedArray())
            }
        }
        updateLastUpdatedPreference()
    }

    private fun fetchPokedexes(){
        val pokedexesCall: Call<PokedexesFromApi> = pokeApi.getPokeApi().getAllPokedexes(50)
        pokedexesCall.enqueue(object :Callback<PokedexesFromApi>{
            override fun onFailure(call: Call<PokedexesFromApi>, t: Throwable) {
                Log.e(TAG,"Error: "+t.message)
            }

            override fun onResponse(
                call: Call<PokedexesFromApi>,
                response: Response<PokedexesFromApi>
            ) {
                response.body()?.let {
                    renewPokedexes(it)
                }
            }
        })
    }

    private fun renewPokedexes(pokedexesFromApi: PokedexesFromApi){
        uiScope.launch {
            withContext(Dispatchers.IO){
                pokedexesDao.deleteAllPokedexes()
                if (!pokedexesFromApi.results.isNullOrEmpty()){
                    val tempList = ArrayList<Pokedexes>()
                    for (pokedex in pokedexesFromApi.results){
                        val pId = try {
                            URI(pokedex.url).path.substringBeforeLast('/').substringAfterLast('/').toInt()
                        }catch (e:Exception){
                            1
                        }
                        tempList.add(Pokedexes(pokedex.name, pId))
                    }
                    pokedexesDao.insert(*tempList.toTypedArray())
                }
            }
        }
    }

    private fun updateLastUpdatedPreference(){
        val sharedPreferencesEditor = application.getSharedPreferences(Pokedex.POKEDEX_SHARED_PREF, Context.MODE_PRIVATE).edit()
        sharedPreferencesEditor.putLong(LAST_UPDATE_DATE, Date().time)
        sharedPreferencesEditor.apply()
    }

    fun getRegions(): LiveData<List<Regions>> = regionsDao.getAllRegions()

    fun getPokedexesByIds(idsList: List<Int>):LiveData<List<Pokedexes>> = pokedexesDao.getPokedexesFromRegion(idsList)

    fun cancelRepositoryJobs() = repositoryJob.cancel()
}