package br.com.renandeldotti.pokedex.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.api.data.PokemonDataFromApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailViewModel: ViewModel() {
    private val pokeApi: RetrofitPokeApi = RetrofitPokeApi()

    companion object {
        private val TAG = PokemonDetailViewModel::class.java.simpleName
    }

    fun getPokemonData(pokemonId:String):LiveData<PokemonDataFromApi>{
        val call = pokeApi.getPokeApi().getPokemonData(pokemonId)
        val tempData = MutableLiveData<PokemonDataFromApi>()
        call.enqueue(object : Callback<PokemonDataFromApi>{
            override fun onFailure(call: Call<PokemonDataFromApi>, t: Throwable) {
                Log.e(TAG, "Failed retrieving data from API\tError: ${t.message}")
            }

            override fun onResponse(
                call: Call<PokemonDataFromApi>,
                response: Response<PokemonDataFromApi>
            ) {
                response.body()?.let {
                    tempData.postValue(it)
                }
            }
        })
        return tempData
    }
}