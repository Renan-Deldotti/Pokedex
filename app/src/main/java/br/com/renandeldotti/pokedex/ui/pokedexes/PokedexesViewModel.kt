package br.com.renandeldotti.pokedex.ui.pokedexes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.renandeldotti.pokedex.database.PokeRepository

class PokedexesViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeRepository: PokeRepository = PokeRepository(application)
}