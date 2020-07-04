package br.com.renandeldotti.pokedex.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FetchPokeApi {

    //Get all regions with it's url and id
    @GET("region")
    fun getRegions():Call<String>

    //Get the Pokedexes from a defined region
    @GET("regions/{id}")
    fun getPokedexesFromRegion(@Path("id") regionId:Int)

    //Get all Pokemon from a Pokedex
    @GET("pokedex/{id}/")
    fun getPokemonFromPokedex(@Path("id") pokedexId:Int)
}