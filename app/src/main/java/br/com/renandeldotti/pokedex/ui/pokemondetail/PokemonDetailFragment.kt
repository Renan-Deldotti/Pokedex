package br.com.renandeldotti.pokedex.ui.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.renandeldotti.pokedex.MainActivity
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.databinding.FragmentPokemonDetailBinding

class PokemonDetailFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //(activity as MainActivity).changeAppBarTitle("")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container, false)
        return binding.root
    }
}