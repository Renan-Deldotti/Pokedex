package br.com.renandeldotti.pokedex.api

import br.com.renandeldotti.pokedex.api.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface FetchPokeApi {

    //Get all regions with it's url and id
    @GET("region")
    fun getRegions(): Call<Region>

    //Get the Pokedexes from a defined region
    @GET("region/{id}")
    fun getPokedexesFromRegion(@Path("id") regionId: String): Call<RegionPokedexes>

    //Get all Pokemon from a Pokedex
    //Obs: 1 has all pokemon
    @GET("pokedex/{id}")
    fun getPokemonFromPokedex(@Path("id") pokedexId: String): Call<PokemonListFromPokedex>

    //Get pokemon data
    @GET("pokemon/{id}")
    fun getPokemonData(@Path("id") pokemonID: String): Call<PokemonDataFromApi>

    //Get pokemon description
    @GET("pokemon-species/{id}")
    fun getPokemonDesc(@Path("id") pokemonID: String)

    //Get all pokemon
    @GET("pokemon")
    fun getAllPokemon(@QueryMap options: Map<String, String>): Call<PokemonListFromApi>

    // Get all pokemon by pages
    @GET("https://pokeapi.co/api/v2/pokemon?offset=0&limit=50")
    fun getAllPokemonByPage()
}