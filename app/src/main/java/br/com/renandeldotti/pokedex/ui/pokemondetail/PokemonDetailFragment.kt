package br.com.renandeldotti.pokedex.ui.pokemondetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.renandeldotti.pokedex.MainActivity
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.databinding.FragmentPokemonDetailBinding

class PokemonDetailFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel
    private val args: PokemonDetailFragmentArgs by navArgs()

    companion object {
        private const val TAG:String = "PokemonDetailFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //(activity as MainActivity).changeAppBarTitle("")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container, false)
        viewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]

        viewModel.getPokemonData(args.pokemonId).observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "onCreateView: ${it.height}")
        })

        return binding.root
    }
}