package br.com.renandeldotti.pokedex.ui.pokedexes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.com.renandeldotti.pokedex.database.PokeRepository
import br.com.renandeldotti.pokedex.database.Pokedexes

class PokedexesViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeRepository: PokeRepository = PokeRepository(application)

    fun getPokedexFromRegion(regionId: Int): LiveData<List<Pokedexes>> = pokeRepository.fetchPokedexesFromRegion(regionId)
}