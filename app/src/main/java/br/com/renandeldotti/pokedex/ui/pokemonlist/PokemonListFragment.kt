package br.com.renandeldotti.pokedex.ui.pokemonlist

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.MainActivity
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.api.data.PokemonSpecies
import br.com.renandeldotti.pokedex.databinding.FragmentPokemonListBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class PokemonListFragment : Fragment(), PokemonListAdapter.PokemonListClickListener {

    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var adapter: PokemonListAdapter
    private val args: PokemonListFragmentArgs by navArgs()

    companion object {
        private val TAG:String = PokemonListFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false)
        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        //val id = PokemonListFragmentArgs.fromBundle(requireArguments()).pokedexId
        val pokedexId = args.pokedexId
        //Log.e("TAG", "onCreateView: $pokedexId" )
        binding.recyclerViewPokemonListAll.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokemonListAll.setHasFixedSize(true)
        adapter = PokemonListAdapter(ArrayList(), this)
        binding.recyclerViewPokemonListAll.adapter = adapter
        if (pokedexId != null && !TextUtils.isEmpty(pokedexId) && pokedexId != "0" && pokedexId != "1"){
            updateRecyclerViewData(pokedexId)
        }else{
            updateRecyclerViewData("1")
        }
        activity?.let {
            val newTitle:String =
                if(args.pokedexName.isNullOrEmpty()) getString(R.string.all_pokemon)
                else args.pokedexName?.toUpperCase(Locale.getDefault())!!
            (it as MainActivity).changeAppBarTitle(newTitle)
        }
        return binding.root
    }

    private fun updateRecyclerViewData(pokedexId: String){
        viewModel.getPokemonListFromPokedex(pokedexId).observe(viewLifecycleOwner, Observer {
            //Log.e(TAG, "ID: $pokedexId\tData: $it ")
            if (!it.pokemon_entries.isNullOrEmpty()){
                adapter = PokemonListAdapter(it.pokemon_entries, this)
                binding.recyclerViewPokemonListAll.adapter = adapter
            }
        })
    }

    override fun selectedPokemon(pokemonSpecies: PokemonSpecies) {
        try{
            val id = URI(pokemonSpecies.url).path.substringBeforeLast('/').substringAfterLast('/').toInt()
            findNavController().navigate(PokemonListFragmentDirections.actionNavPokemonListToPokemonDetailFragment(id.toString()))
        }catch (e:Exception){
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }
}