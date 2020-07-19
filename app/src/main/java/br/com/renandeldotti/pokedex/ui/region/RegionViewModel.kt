package br.com.renandeldotti.pokedex.ui.region

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.api.data.RegionPokedexes
import br.com.renandeldotti.pokedex.database.PokeRepository
import br.com.renandeldotti.pokedex.database.Regions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegionViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeApi: RetrofitPokeApi = RetrofitPokeApi()
    private val pokeRepository:PokeRepository = PokeRepository(application)

    companion object{
        private val TAG:String = RegionViewModel::class.java.simpleName
    }

    fun getRegions(): LiveData<List<Regions>> = pokeRepository.getRegions()

    fun getPokedexesFromRegion(regionId: String): LiveData<RegionPokedexes>{
        val call = pokeApi.getPokeApi().getPokedexesFromRegion(regionId)
        val pokedexesLiveData:MutableLiveData<RegionPokedexes> = MutableLiveData()
        call.enqueue(object : Callback<RegionPokedexes>{
            override fun onFailure(call: Call<RegionPokedexes>, t: Throwable) {
                Log.e(TAG, "Failed retrieving info from API\tError: ${t.message}")
            }

            override fun onResponse(call: Call<RegionPokedexes>,response: Response<RegionPokedexes>) {
                response.body()?.let {pokedexesLiveData.postValue(it)}
            }
        })
        return pokedexesLiveData
    }

    override fun onCleared() {
        super.onCleared()
        pokeRepository.cancelRepositoryJobs()
    }
}