package br.com.renandeldotti.pokedex.ui.region

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.database.Regions
import br.com.renandeldotti.pokedex.databinding.FragmentRegionBinding


class RegionFragment : Fragment(), RegionAdapter.RegionListener {

    private lateinit var regionViewModel: RegionViewModel
    private lateinit var regions: List<Regions>
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

    companion object{
        //private const val TAG:String = "RegionFragment"
    }

    private fun updateRegionsData(){
        regionViewModel.getRegions().observe(viewLifecycleOwner, Observer {
            it?.let {
                val tempList = ArrayList<String>()
                for(region in it){
                    tempList.add(region.name)
                }
                regions = it
                fragmentRegionBinding.recyclerViewRegions.adapter = RegionAdapter(tempList, this)
            }
        })
    }

    override fun selectedRegion(position: Int) {
        if (!checkInternet()){
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
            return
        }
        regionViewModel.getPokedexesFromRegion(regions[position].regionId.toString()).observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                findNavController().navigate(RegionFragmentDirections.actionNavRegionsToPokedexesFragment(it.toIntArray()))
            }
        })
    }

    private fun checkInternet():Boolean{
        val connectivityManager: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            connectivityManager.activeNetworkInfo?.let {return it.isConnectedOrConnecting}
        }
        return false
    }

    /*private fun test(){
        Log.e("SUSPEND FUN TESTE", "Start \tThread name: "+ Thread.currentThread().name)
        CoroutineScope(Dispatchers.Main).launch { doTestWork(0L) }
        Log.e("SUSPEND FUN TESTE", "After Start \tThread name: "+ Thread.currentThread().name) // Executes immediately after the other log message
    }

    private suspend fun doTestWork(i:Long){
        var i = i
        withContext(Dispatchers.Default){
            Log.e("SUSPEND FUN TESTE", "Running\tThread name: "+ Thread.currentThread().name)
            i = waitTwoSecs(i)
            Log.e("SUSPEND FUN TESTE", "Waited 2 seconds \tThread name: "+ Thread.currentThread().name)
            i = waitThreeSecs(i)
            Log.e("SUSPEND FUN TESTE", "Waited 3 seconds $i \tThread name: "+ Thread.currentThread().name)
        }
        Log.e("SUSPEND FUN TESTE", "Finished i = $i\tThread name: "+ Thread.currentThread().name)
    }

    suspend fun waitTwoSecs(i:Long):Long{
        var i = i
        while (i <= 2000){
            Log.e("SUSPEND FUN TESTE", "Running 2 seconds i = $i\tThread name: "+ Thread.currentThread().name)
            delay(1000)
            i+= 1000
        }
        return i
    }

    suspend fun waitThreeSecs(i:Long):Long{
        var i = i
        while (i <= 5000){
            Log.e("SUSPEND FUN TESTE", "Running 3 seconds i = $i\tThread name: "+ Thread.currentThread().name)
            delay(1000)
            i+= 1000
        }
        return i
    }*/
}