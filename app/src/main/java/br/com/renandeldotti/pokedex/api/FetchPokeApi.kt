package br.com.renandeldotti.pokedex.api

import br.com.renandeldotti.pokedex.api.data.PokemonListFromApi
import br.com.renandeldotti.pokedex.api.data.Region
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface FetchPokeApi {

    //Get all regions with it's url and id
    @GET("region")
    fun getRegions(): Call<Region>

    //Get the Pokedexes from a defined region
    @GET("regions/{id}")
    fun getPokedexesFromRegion(@Path("id") regionId: Int)

    //Get all Pokemon from a Pokedex
    @GET("pokedex/{id}/")
    fun getPokemonFromPokedex(@Path("id") pokedexId: Int)

    //Get pokemon info
    @GET("pokemon/{id}/")
    fun getPokemonInfo(@Path("id") pokemonID: Int)

    //Get all pokemon
    @GET("pokemon")
    fun getAllPokemon(@QueryMap options: Map<String, String>): Call<PokemonListFromApi>
}