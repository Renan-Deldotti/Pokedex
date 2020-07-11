package br.com.renandeldotti.pokedex.ui.region

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.renandeldotti.pokedex.database.PokeRepository
import br.com.renandeldotti.pokedex.database.Region

class RegionViewModel(application: Application) : AndroidViewModel(application) {
    private val pokeRepository:PokeRepository = PokeRepository(application)

    companion object{
        val TAG:String = RegionViewModel::class.java.simpleName
    }

    fun getRegions(): LiveData<List<Region>> = pokeRepository.getAllRegions()

    override fun onCleared() {
        super.onCleared()
        pokeRepository.cancelRepositoryJobs()
    }
}