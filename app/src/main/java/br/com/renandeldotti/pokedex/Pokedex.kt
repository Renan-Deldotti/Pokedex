package br.com.renandeldotti.pokedex

import android.app.Application

class Pokedex : Application(){
    companion object{
        const val POKEDEX_SHARED_PREF:String = "br.com.renandeldotti.pokedex.SHARED_PREFS_001"
    }
}