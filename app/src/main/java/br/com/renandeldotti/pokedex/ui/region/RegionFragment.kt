package br.com.renandeldotti.pokedex.ui.region

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.R
import kotlinx.android.synthetic.main.fragment_region.view.*


class RegionFragment : Fragment(), RegionAdapter.RegionListener {

    private lateinit var regionsList: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_region, container, false)

        regionsList = ArrayList()
        (regionsList as ArrayList<String>).add("Kanto")
        (regionsList as ArrayList<String>).add("Kanto")
        (regionsList as ArrayList<String>).add("Kanto")
        (regionsList as ArrayList<String>).add("Kanto")
        (regionsList as ArrayList<String>).add("Kanto")

        view.recyclerView_regions.layoutManager = GridLayoutManager(context,2)
        view.recyclerView_regions.setHasFixedSize(true)
        view.recyclerView_regions.adapter = RegionAdapter(regionsList, this)

        return view
    }

    companion object {
    }

    override fun selectedRegion(position: Int) {
        Toast.makeText(context, "test+"+regionsList[position], Toast.LENGTH_SHORT).show()
    }
}