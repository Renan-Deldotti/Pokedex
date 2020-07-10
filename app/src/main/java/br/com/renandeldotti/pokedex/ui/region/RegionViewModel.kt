package br.com.renandeldotti.pokedex.ui.region

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.data.Region
import br.com.renandeldotti.pokedex.database.PokeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegionViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeApi:RetrofitPokeApi = RetrofitPokeApi()
    private val pokeRepository:PokeRepository = PokeRepository(application)
    val regionsMutableLiveData:MutableLiveData<Region> = MutableLiveData()

    companion object{
        val TAG:String = RegionViewModel::class.java.simpleName
    }

    init {
        fetchRegionsData()
    }

    private fun fetchRegionsData(){
        val regionCall:Call<Region> = pokeApi.getPokeApi().getRegions()
        regionCall.enqueue(object : Callback<Region>{
            override fun onFailure(call: Call<Region>, t: Throwable) {
                Log.e(TAG,"Error: "+t.message)
            }

            override fun onResponse(call: Call<Region>, response: Response<Region>) {
                response.body()?.let {
                    regionsMutableLiveData.postValue(it)
                }
            }
        })
    }

    fun getAllRegions():LiveData<List<br.com.renandeldotti.pokedex.database.Region>> = pokeRepository.getAllRegions()

    fun renewRegions(vararg region: br.com.renandeldotti.pokedex.database.Region){
        pokeRepository.renewRegionsData(*region)
    }

    override fun onCleared() {
        super.onCleared()
        pokeRepository.cancelRepositoryJobs()
    }
}