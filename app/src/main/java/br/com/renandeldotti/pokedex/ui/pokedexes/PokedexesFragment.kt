package br.com.renandeldotti.pokedex.ui.pokedexes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.database.Pokedexes
import br.com.renandeldotti.pokedex.databinding.FragmentPokedexesBinding

class PokedexesFragment : Fragment(), PokedexesAdapter.PokedexAdapterListener {

    private lateinit var binding: FragmentPokedexesBinding
    private lateinit var viewModel: PokedexesViewModel
    private val args: PokedexesFragmentArgs by navArgs()

    companion object{
        private const val TAG = "PokedexesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[PokedexesViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pokedexes,container,false)

        binding.recyclerViewPokedexes.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokedexes.adapter = PokedexesAdapter(ArrayList(), this)

        viewModel.getPokedexesByIds(args.pokedexesIds.toList()).observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                binding.recyclerViewPokedexes.adapter = PokedexesAdapter(it,this)
            }
        })

        return binding.root
    }

    override fun selectedPokedexPosition(pokedex: Pokedexes) {
        findNavController().navigate(PokedexesFragmentDirections.actionPokedexesFragmentToNavPokemonList(pokedex.pokedexId.toString(), pokedex.name))
    }
}