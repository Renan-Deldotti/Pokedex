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
    fun toPokedexList(gsonVal: String): List<Pokedex>{
        val gson = Gson()
        val type = object : TypeToken<List<Pokedex>>(){}.type
        return gson.fromJson(gsonVal, type)
    }
}