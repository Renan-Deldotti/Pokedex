package br.com.renandeldotti.pokedex.ui.region

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.data.Region
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegionViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeApi:RetrofitPokeApi = RetrofitPokeApi()
    val regionsMutableLiveData:MutableLiveData<Region> = MutableLiveData()

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

    companion object{
        val TAG:String = RegionViewModel::class.java.simpleName
    }
}