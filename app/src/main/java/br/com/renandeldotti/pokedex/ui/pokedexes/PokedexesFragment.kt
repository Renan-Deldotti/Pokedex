package br.com.renandeldotti.pokedex.ui.pokedexes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.databinding.FragmentPokedexesBinding

class PokedexesFragment : Fragment(), PokedexesAdapter.PokedexAdapterListener {

    private lateinit var binding: FragmentPokedexesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pokedexes,container,false)

        binding.recyclerViewPokedexes.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokedexes.adapter = PokedexesAdapter(ArrayList(), this)

        return binding.root
    }

    override fun selectedPokedexPosition(position: Int) {

    }
}