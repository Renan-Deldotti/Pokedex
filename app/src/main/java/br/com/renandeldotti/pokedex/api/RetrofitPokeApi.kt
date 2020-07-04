package br.com.renandeldotti.pokedex.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPokeApi {

    companion object {
        private const val url: String = "https://pokeapi.co/api/v2/"
    }

    private val retrofitInstance: Retrofit

    init {
        retrofitInstance = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getPokeApi():FetchPokeApi = retrofitInstance.create(FetchPokeApi::class.java)
}