package br.com.renandeldotti.pokedex.ui.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renandeldotti.pokedex.R
import kotlinx.android.synthetic.main.item_region.view.*
import java.util.*

class RegionAdapter(private val regionsList: List<String>, private val regionListener: RegionListener): RecyclerView.Adapter<RegionAdapter.RegionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return RegionHolder(view, regionListener)
    }

    override fun getItemCount(): Int {
        return regionsList.size
    }

    override fun onBindViewHolder(holder: RegionHolder, position: Int) {
        holder.textViewRegion.text = regionsList[position]
    }

    class RegionHolder(itemView: View, regionListener: RegionListener) :RecyclerView.ViewHolder(itemView){
        var textViewRegion = itemView.textView_region

        init {
            itemView.setOnClickListener{
                if (adapterPosition != RecyclerView.NO_POSITION){
                    regionListener.selectedRegion(adapterPosition)
                }
            }
        }
    }

    interface RegionListener{
        fun selectedRegion(position:Int)
    }
}