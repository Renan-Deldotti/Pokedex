package br.com.renandeldotti.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import br.com.renandeldotti.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(activityMainBinding.root)

        val drawerLayout = activityMainBinding.drawerLayout
        val navView = activityMainBinding.navigationView
        configuration = AppBarConfiguration.Builder(R.id.nav_regions, R.id.nav_pokemonList)
            .setDrawerLayout(drawerLayout)
            .build()

        navController = findNavController(R.id.fragment_nav_host)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, configuration) || super.onSupportNavigateUp()
    }
}