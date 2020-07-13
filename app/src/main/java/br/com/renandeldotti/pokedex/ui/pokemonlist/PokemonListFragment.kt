package br.com.renandeldotti.pokedex.ui.pokemonlist

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.databinding.FragmentPokemonListBinding

class PokemonListFragment : Fragment() {

    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var viewModel: PokemonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false)
        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        //val id = PokemonListFragmentArgs.fromBundle(requireArguments()).pokedexId
        val args: PokemonListFragmentArgs by navArgs()
        val pokedexId = args.pokedexId
        //Log.e("TAG", "onCreateView: $pokedexId" )
        if (!TextUtils.isEmpty(pokedexId) && !pokedexId.equals("0")){
            //
        }

        return binding.root
    }

    companion object {
    }
}