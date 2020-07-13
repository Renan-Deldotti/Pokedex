package br.com.renandeldotti.pokedex.ui.pokemonlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.api.RetrofitPokeApi
import br.com.renandeldotti.pokedex.api.data.PokemonListFromPokedex
import br.com.renandeldotti.pokedex.database.PokeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeApi: RetrofitPokeApi = RetrofitPokeApi()
    private val pokeRepository: PokeRepository = PokeRepository(application)

    companion object {
        private val TAG = PokemonListViewModel::class.java.simpleName
    }

    fun getPokemonListFromPokedex(pokedexId: String): LiveData<PokemonListFromPokedex> {
        val call = pokeApi.getPokeApi().getPokemonFromPokedex(pokedexId)
        val tempData = MutableLiveData<PokemonListFromPokedex>()
        call.enqueue(object : Callback<PokemonListFromPokedex> {
            override fun onFailure(call: Call<PokemonListFromPokedex>, t: Throwable) {
                Log.e(TAG, "Failed retrieving info from API\tError: ${t.message}")
            }

            override fun onResponse(
                call: Call<PokemonListFromPokedex>,
                response: Response<PokemonListFromPokedex>
            ) {
                response.body()?.let {
                    tempData.postValue(it)
                }
            }
        })
        return tempData
    }
}