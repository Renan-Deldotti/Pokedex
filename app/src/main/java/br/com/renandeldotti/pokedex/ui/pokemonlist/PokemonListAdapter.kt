package br.com.renandeldotti.pokedex.ui.pokemonlist

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.api.data.PokemonEntries
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_pokemon.view.*
import java.net.URI
import java.util.*

class PokemonListAdapter(private val pokemonList:List<PokemonEntries>): RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val thisPokemon = pokemonList[position].pokemon_species
        //Log.e("TAG", "onBindViewHolder: $thisPokemon" )
        if (!TextUtils.isEmpty(thisPokemon.name)){
            holder.pokemonName.text = thisPokemon.name.capitalize(Locale.getDefault())
        }else{
            holder.pokemonName.setText(R.string.no_name)
        }
        val pokeId:String = URI(thisPokemon.url).path.substringBeforeLast('/').substringAfterLast('/')
        Glide.with(holder.pokemonImage).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokeId.png").placeholder(R.drawable.ditto_shiny_placeholder).into(holder.pokemonImage)
    }


    inner class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.item_pokemon_imageView
        val pokemonName: TextView = itemView.item_pokemon_textView
    }
}