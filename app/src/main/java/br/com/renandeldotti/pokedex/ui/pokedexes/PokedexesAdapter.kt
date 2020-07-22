package br.com.renandeldotti.pokedex.ui.pokedexes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.database.Pokedexes
import kotlinx.android.synthetic.main.item_region.view.*
import java.util.*

class PokedexesAdapter(
    private val pokedexesNamesList: List<Pokedexes>,
    private val pokedexesAdapterListener: PokedexAdapterListener
) : RecyclerView.Adapter<PokedexesAdapter.PokedexesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexesViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return PokedexesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokedexesNamesList.size
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: PokedexesViewHolder, position: Int) {
        holder.pokedexNameTV.text = pokedexesNamesList[position].name.capitalize(Locale.getDefault())
    }

    inner class PokedexesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pokedexNameTV: TextView = itemView.textView_region

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    pokedexesAdapterListener.selectedPokedexPosition(adapterPosition)
                }
            }
        }
    }

    interface PokedexAdapterListener {
        fun selectedPokedexPosition(position: Int)
    }
}