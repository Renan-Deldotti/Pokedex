package br.com.renandeldotti.pokedex.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    /*@TypeConverter
    fun fromPokedexList(list: List<Pokedex>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Pokedex>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToPokedexList(jsonString: String): List<Pokedex> {
        val gson = Gson()
        val type = object : TypeToken<List<Pokedex>>() {}.type
        return gson.fromJson(jsonString, type)
    }*/

    @TypeConverter
    fun fromStatsList(list: List<PokemonStats>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToStatsList(jsonString: String): List<PokemonStats> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonStats>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun fromTypesList(list: List<PokemonTypes>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonTypes>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToTypesList(jsonString: String): List<PokemonTypes> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonTypes>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}