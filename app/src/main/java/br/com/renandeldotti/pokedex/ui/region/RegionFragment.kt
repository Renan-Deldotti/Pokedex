package br.com.renandeldotti.pokedex.ui.region

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
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.data.Region
import br.com.renandeldotti.pokedex.databinding.FragmentRegionBinding


class RegionFragment : Fragment(), RegionAdapter.RegionListener {

    private lateinit var regionViewModel: RegionViewModel
    private lateinit var regions:Region
    //private var isConnectedToInternet:Boolean = false
    private lateinit var fragmentRegionBinding: FragmentRegionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        fragmentRegionBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_region,container,false)
        regionViewModel = ViewModelProvider(this).get(RegionViewModel::class.java)

        fragmentRegionBinding.recyclerViewRegions.layoutManager = GridLayoutManager(context,2)
        fragmentRegionBinding.recyclerViewRegions.adapter = RegionAdapter(ArrayList(),this)
        updateRegionsData()

        return fragmentRegionBinding.root
    }

    private fun updateRegionsData(){
        regionViewModel.regionsMutableLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                val regionsList = ArrayList<String>()
                regionsList.clear()
                for ( result in it.results){
                    regionsList.add(result.name)
                }
                fragmentRegionBinding.recyclerViewRegions.adapter = RegionAdapter(regionsList, this)
                regions = it
                updateDb()
            }
            //Log.e("TAG", "updateRegionsData: ")
        })
    }

    private fun updateDb(){
        val regionsToUpdate:ArrayList<br.com.renandeldotti.pokedex.database.Region> = ArrayList()
        for(r in regions.results){
            regionsToUpdate.add(br.com.renandeldotti.pokedex.database.Region(r.name,r.url))
        }

        for(r in regionsToUpdate){
            Log.e("TEEWQNE",""+r.regionName)
        }

        regionViewModel.insertRegions(*regionsToUpdate.toTypedArray())
        showData()
    }
    private fun showData(){
        regionViewModel.getAllRegions().observe(viewLifecycleOwner, Observer {

            Log.e("TESTE", "Data: $it")
        })
    }

    override fun selectedRegion(position: Int) {
        Toast.makeText(context, "test+"+regions.results[position].name, Toast.LENGTH_SHORT).show()
    }
}