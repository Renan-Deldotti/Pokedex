package br.com.renandeldotti.pokedex.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromPokedexList(pokedexList: List<Pokedex>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Pokedex>>() {}.type
        return gson.toJson(pokedexList, type)
    }

    @TypeConverter
    fun toPokedexList(gsonVal: String): List<Pokedex> {
        val gson = Gson()
        val type = object : TypeToken<List<Pokedex>>() {}.type
        return gson.fromJson(gsonVal, type)
    }

    @TypeConverter
    fun fromPokemonStatsList(pokemonStatList: List<PokemonStats>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.toJson(pokemonStatList, type)
    }

    @TypeConverter
    fun toPokemonStatsList(gsonVal: String): List<PokemonStats> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.fromJson(gsonVal, type)
    }

    @TypeConverter
    fun fromPokemonTypesList(pokemonTypesList: List<PokemonTypes>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonTypes>>() {}.type
        return gson.toJson(pokemonTypesList, type)
    }

    @TypeConverter
    fun toPokemonTypesList(gsonVal: String): List<PokemonTypes> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonTypes>>() {}.type
        return gson.fromJson(gsonVal, type)
    }
}